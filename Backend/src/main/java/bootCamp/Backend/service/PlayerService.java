package bootCamp.Backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import bootCamp.Backend.DTO.PlayerDTO;
import bootCamp.Backend.DTO.ResponsePlayerDTO;
import bootCamp.Backend.model.Player;
import bootCamp.Backend.repository.IPlayer;

@Service
public class PlayerService {

    @Autowired
    private IPlayer playerRepository;

    
 public ResponsePlayerDTO createPlayer(String playerName) {
    if (playerName == null || playerName.trim().isEmpty()) {
        return new ResponsePlayerDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre del jugador no puede estar vacío");
    }
    if (playerName.length() > 20) {
        return new ResponsePlayerDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre del jugador no puede exceder los 20 caracteres");
    }
    Player player = new Player(playerName);
    Player savedPlayer = playerRepository.save(player);

    PlayerDTO playerDTO = new PlayerDTO(savedPlayer.getPlayerID(), savedPlayer.getPlayerName());

    return new ResponsePlayerDTO(HttpStatus.OK.toString(), "El jugador ha sido creado exitosamente", playerDTO);
}
}
