package bootCamp.Backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import bootCamp.Backend.model.Player;
import bootCamp.Backend.repository.IPlayer;
import bootCamp.Backend.DTO.ResponseDTO;

@Service
public class PlayerService {

    @Autowired
    private IPlayer playerRepository;

    
    //crear jugador
    public ResponseDTO createPlayer(String playerName) {
        if (playerName == null || playerName.trim().isEmpty()) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre del jugador no puede estar vacío");
        }
        if (playerName.length() > 20) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre del jugador no puede exceder los 20 caracteres");
        }
        Player player = new Player(playerName);
        playerRepository.save(player);
        return new ResponseDTO(HttpStatus.OK.toString(), "El jugador ha sido creado exitosamente");
    }
}
