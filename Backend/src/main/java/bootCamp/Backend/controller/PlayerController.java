package bootCamp.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bootCamp.Backend.DTO.PlayerDTO;
import bootCamp.Backend.DTO.ResponseDTO;
import bootCamp.Backend.service.PlayerService;


@RestController
@RequestMapping("/Player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/CreatePlayer")
    public ResponseEntity<ResponseDTO> insertPlayer(@RequestBody PlayerDTO playerDTO) {
        ResponseDTO response = playerService.createPlayer(playerDTO.getPlayerName());
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

}
