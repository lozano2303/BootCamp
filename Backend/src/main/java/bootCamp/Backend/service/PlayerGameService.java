package bootCamp.Backend.service;

import bootCamp.Backend.DTO.PlayerGameDTO;
import bootCamp.Backend.DTO.ResponseDTO;
import bootCamp.Backend.model.Game;
import bootCamp.Backend.model.Player;
import bootCamp.Backend.model.PlayerGame;
import bootCamp.Backend.model.ids.Player_GameID;
import bootCamp.Backend.repository.IGame;
import bootCamp.Backend.repository.IPlayer;
import bootCamp.Backend.repository.IPlayerGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.Collections;

@Service
public class PlayerGameService {

    @Autowired
    private IPlayerGame playerGameRepository;
    
    @Autowired
    private IPlayer playerRepository;
    
    @Autowired
    private IGame gameRepository;

    @Transactional
    public ResponseDTO assignPlayersToGame(int gameId, List<Integer> playerIds) {
        try {
            // 1. Validaciones básicas
            if (playerIds == null || playerIds.size() < 2 || playerIds.size() > 7) {
                return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), 
                    "Debe asignar entre 2 y 7 jugadores al juego.");
            }

            // 2. Verificar que el juego existe
            Optional<Game> gameOpt = gameRepository.findById(gameId);
            if (!gameOpt.isPresent()) {
                return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), 
                    "El juego especificado no existe.");
            }
            Game game = gameOpt.get();

            // 3. Verificar que todos los jugadores existen
            List<Player> players = new ArrayList<>();
            for (Integer playerId : playerIds) {
                Optional<Player> playerOpt = playerRepository.findById(playerId);
                if (!playerOpt.isPresent()) {
                    return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), 
                        "El jugador con ID " + playerId + " no existe.");
                }
                players.add(playerOpt.get());
            }

            // 4. Asignar jugadores al juego
            List<PlayerGameDTO> assignments = new ArrayList<>();
            for (Player player : players) {
                // Verificar si ya está asignado usando el método corregido
                if (playerGameRepository.existsPlayerInGame(gameId, player.getPlayerID())) {
                    continue; // Ya está asignado, saltar
                }

                // Crear nueva asignación
                Player_GameID id = new Player_GameID();
                id.setGameID(gameId);
                id.setPlayerID(player.getPlayerID());
                
                PlayerGame playerGame = new PlayerGame();
                playerGame.setId(id); // Usando el nombre corregido (id en lugar de player_GameID)
                playerGame.setPlayer(player); // Nombre corregido
                playerGame.setGame(game); // Nombre corregido
                playerGame.setScore(0); // Puntuación inicial 0

                playerGameRepository.save(playerGame);
                assignments.add(new PlayerGameDTO(
                    gameId,
                    player.getPlayerID(),
                    0
                ));
            }

            return new ResponseDTO(HttpStatus.OK.toString(), 
                "Jugadores asignados correctamente al juego.", 
                assignments);
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
                "Error al asignar jugadores: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseDTO updatePlayerScores(int gameId, Map<Integer, Integer> playerScores) {
        try {
            // 1. Validaciones básicas
            if (playerScores == null || playerScores.isEmpty()) {
                return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), 
                    "Debe proporcionar los puntajes de los jugadores.");
            }

            // 2. Verificar que el juego existe
            if (!gameRepository.existsById(gameId)) {
                return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), 
                    "El juego especificado no existe.");
            }

            // 3. Actualizar puntajes
            List<PlayerGameDTO> updatedScores = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : playerScores.entrySet()) {
                int playerId = entry.getKey();
                int score = entry.getValue();

                // Verificar que el jugador existe
                Optional<Player> playerOpt = playerRepository.findById(playerId);
                if (!playerOpt.isPresent()) {
                    return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), 
                        "El jugador con ID " + playerId + " no existe.");
                }

                // Buscar la relación player-game usando el ID compuesto
                Player_GameID id = new Player_GameID();
                id.setGameID(gameId);
                id.setPlayerID(playerId);

                Optional<PlayerGame> playerGameOpt = playerGameRepository.findById(id);
                if (!playerGameOpt.isPresent()) {
                    return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), 
                        "El jugador " + playerId + " no está asignado a este juego.");
                }

                // Actualizar puntaje
                PlayerGame playerGame = playerGameOpt.get();
                playerGame.setScore(score);
                playerGameRepository.save(playerGame);

                updatedScores.add(new PlayerGameDTO(
                    gameId, 
                    playerId, 
                    score
                ));
            }

            return new ResponseDTO(HttpStatus.OK.toString(), 
                "Puntajes actualizados correctamente.", 
                updatedScores);
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
                "Error al actualizar puntajes: " + e.getMessage());
        }
    }

    // Método adicional para obtener el ganador
    @Transactional(readOnly = true)
    public ResponseDTO getGameWinner(int gameId) {
        try {
            List<PlayerGame> results = playerGameRepository.findWinner(gameId);
            if (results.isEmpty()) {
                return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), 
                    "No hay jugadores en este juego.");
            }
            
            PlayerGame winner = results.get(0);
            // Crear lista con un solo elemento para mantener consistencia
            List<PlayerGameDTO> winnerList = Collections.singletonList(
                new PlayerGameDTO(
                    gameId,
                    winner.getPlayer().getPlayerID(),
                    winner.getScore()
                )
            );
            
            return new ResponseDTO(HttpStatus.OK.toString(), 
                "Ganador encontrado", 
                winnerList); // Ahora envías una List
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
                "Error al buscar ganador: " + e.getMessage());
        }
    }
}