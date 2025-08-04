package bootCamp.Backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bootCamp.Backend.DTO.PlayerGameDTO;
import bootCamp.Backend.DTO.ResponseDTO;
import bootCamp.Backend.model.Game;
import bootCamp.Backend.model.Player;
import bootCamp.Backend.model.PlayerGame;
import bootCamp.Backend.model.ids.Player_GameID;
import bootCamp.Backend.repository.IGame;
import bootCamp.Backend.repository.IPlayer;
import bootCamp.Backend.repository.IPlayerGame;

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
            if (playerIds == null || playerIds.size() < 2 || playerIds.size() > 7) {
                return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), 
                    "Debe asignar entre 2 y 7 jugadores al juego.");
            }

            Optional<Game> gameOpt = gameRepository.findById(gameId);
            if (!gameOpt.isPresent()) {
                return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), 
                    "El juego especificado no existe.");
            }
            Game game = gameOpt.get();

            List<Player> players = new ArrayList<>();
            for (Integer playerId : playerIds) {
                Optional<Player> playerOpt = playerRepository.findById(playerId);
                if (!playerOpt.isPresent()) {
                    return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), 
                        "El jugador con ID " + playerId + " no existe.");
                }
                players.add(playerOpt.get());
            }

            List<PlayerGameDTO> assignments = new ArrayList<>();
            for (Player player : players) {
                if (playerGameRepository.existsPlayerInGame(gameId, player.getPlayerID())) {
                    continue;
                }

                Player_GameID id = new Player_GameID();
                id.setGameID(gameId);
                id.setPlayerID(player.getPlayerID());
                
                PlayerGame playerGame = new PlayerGame();
                playerGame.setId(id);
                playerGame.setPlayer(player);
                playerGame.setGame(game);
                playerGame.setScore(0);

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
            if (playerScores == null || playerScores.isEmpty()) {
                return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), 
                    "Debe proporcionar los puntajes de los jugadores.");
            }

            if (!gameRepository.existsById(gameId)) {
                return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), 
                    "El juego especificado no existe.");
            }

            List<PlayerGameDTO> updatedScores = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : playerScores.entrySet()) {
                int playerId = entry.getKey();
                int score = entry.getValue();

                Optional<Player> playerOpt = playerRepository.findById(playerId);
                if (!playerOpt.isPresent()) {
                    return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), 
                        "El jugador con ID " + playerId + " no existe.");
                }

                Player_GameID id = new Player_GameID();
                id.setGameID(gameId);
                id.setPlayerID(playerId);

                Optional<PlayerGame> playerGameOpt = playerGameRepository.findById(id);
                if (!playerGameOpt.isPresent()) {
                    return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), 
                        "El jugador " + playerId + " no está asignado a este juego.");
                }

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

    @Transactional(readOnly = true)
    public ResponseDTO getGameWinner(int gameId) {
        try {
            List<PlayerGame> results = playerGameRepository.findWinner(gameId);
            if (results.isEmpty()) {
                return new ResponseDTO(HttpStatus.NOT_FOUND.toString(), 
                    "No hay jugadores en este juego.");
            }
            
            PlayerGame winner = results.get(0);
            List<PlayerGameDTO> winnerList = Collections.singletonList(
                new PlayerGameDTO(
                    gameId,
                    winner.getPlayer().getPlayerID(),
                    winner.getScore()
                )
            );
            
            return new ResponseDTO(HttpStatus.OK.toString(), 
                "Ganador encontrado", 
                winnerList);
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
                "Error al buscar ganador: " + e.getMessage());
        }
    }

    // --------- MÉTODO NUEVO PARA OBTENER LOS JUGADORES POR JUEGO ---------
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getPlayersByGame(int gameId) {
        List<Map<String, Object>> result = new ArrayList<>();

        List<PlayerGame> playerGames = playerGameRepository.findByGameId(gameId);

        for (PlayerGame pg : playerGames) {
            Map<String, Object> map = new HashMap<>();
            Player player = pg.getPlayer();

            if (player != null) {
                map.put("playerID", player.getPlayerID());
                map.put("playerName", player.getPlayerName()); // Cambia si tu getter es distinto
            } else {
                map.put("playerID", null);
                map.put("playerName", "Desconocido");
            }

            result.add(map);
        }

        return result;
    }

}
