package bootCamp.Backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.sql.Time;
import org.springframework.http.HttpStatus;


import bootCamp.Backend.repository.IGame;
import bootCamp.Backend.DTO.GameDTO;
import bootCamp.Backend.DTO.ResponseDTO;
import bootCamp.Backend.model.Game;
import bootCamp.Backend.repository.IPlayerGame;
import bootCamp.Backend.repository.IPlayer;
import bootCamp.Backend.model.PlayerGame;
import bootCamp.Backend.model.Player;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private IGame gameRepository;
    @Autowired
    private IPlayerGame playerGameRepository;
    @Autowired
    private IPlayer playerRepository;

    private final List<Integer> allowedMinutes = Arrays.asList(1,2,3,4,5);

    //crear juego
    public ResponseDTO createGame(GameDTO gameDTO) {
        int cuantityPlayers = gameDTO.getCuantityPlayers();
        Time gameTime = gameDTO.getGameTime();
        //cantidad de jugadores debe estar entre 2 y 7
        if (gameDTO.getCuantityPlayers() < 2 || gameDTO.getCuantityPlayers() > 7) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "La cantidad de jugadores debe estar entre 2 y 7 jugadores.");
        }
        //tiempo entre los minutos permitidos
        int minutes = gameTime.toLocalTime().getMinute();
        int hours = gameTime.toLocalTime().getHour();
        int seconds = gameTime.toLocalTime().getSecond();
        //tiempo entre 1 y 5 minutos
        if (hours != 0 || seconds != 0 || !allowedMinutes.contains(minutes)) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "El tiempo del juego debe ser entre 1 y 5 minutos.");
        }
        
        Game game = new Game();
        game.setCuantityPlayers(cuantityPlayers);
        game.setGameTime(gameTime);
        game.setWinner(null);

        gameRepository.save(game);

        return new ResponseDTO(HttpStatus.OK .toString(), "Partida creada exitosamente.");
    }

    //establecer ganador
    public ResponseDTO insertGameWinner(int gameID) {
        //buscar jugadores de la partida
        List<PlayerGame> participants = playerGameRepository.findWinner(gameID);

        if (participants == null || participants.isEmpty()) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "No hay jugadores dentro del juego.");
        }

        PlayerGame winnerPlayerGame = participants.get(0);

        Optional<Player> winnerPlayerOpt = playerRepository.findById(winnerPlayerGame.getPlayerID().getPlayerID());
        if (!winnerPlayerOpt.isPresent()) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "Jugador no encontrado.");
        }
        String winnerName = winnerPlayerGame.getPlayerID().getPlayerName();

        // 4. Actualizar el campo winner en Game
        Optional<Game> gameOpt = gameRepository.findById(gameID);
        if (!gameOpt.isPresent()) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "juego no existente.");
        }

        Game game = gameOpt.get();
        game.setWinner(winnerName);
        gameRepository.save(game);

        return new ResponseDTO(HttpStatus.OK.toString(), "Ganador de la partida: " + winnerName);
    }
    }
