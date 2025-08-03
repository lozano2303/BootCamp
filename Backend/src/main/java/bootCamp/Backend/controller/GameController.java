package bootCamp.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bootCamp.Backend.service.GameService;
import bootCamp.Backend.DTO.GameDTO;
import bootCamp.Backend.DTO.ResponseDTO;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    // Crear partida
    @PostMapping("/CreateGame")
    public ResponseEntity<ResponseDTO> createGame(@RequestBody GameDTO gameDTO) {
        ResponseDTO response = gameService.createGame(gameDTO);
        return ResponseEntity.status(response.getStatus().equals("OK") ? 200 : 400).body(response);
    }

    // Establecer ganador por gameID
    @PutMapping("/setWinner/{gameID}")
    public ResponseEntity<ResponseDTO> setGameWinner(@PathVariable int gameID) {
        ResponseDTO response = gameService.insertGameWinner(gameID);
        return ResponseEntity.status(response.getStatus().equals("OK") ? 200 : 400).body(response);
    }
}