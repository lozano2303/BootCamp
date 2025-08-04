package bootCamp.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bootCamp.Backend.DTO.GameDTO;
import bootCamp.Backend.DTO.ResponseDTO;
import bootCamp.Backend.service.GameService;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    // Crear partida
    @PostMapping("/CreateGame")
    public ResponseEntity<ResponseDTO> createGame(@RequestBody GameDTO gameDTO) {
        ResponseDTO response = gameService.createGame(gameDTO);
        HttpStatus status = response.getStatus().equals(HttpStatus.OK.toString()) 
                            ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    // Establecer ganador por gameID
    @PutMapping("/setWinner/{gameID}")
    public ResponseEntity<ResponseDTO> setGameWinner(@PathVariable int gameID) {
        ResponseDTO response = gameService.insertGameWinner(gameID);
        return ResponseEntity.status(response.getStatus().equals("OK") ? 200 : 400).body(response);
    }
}