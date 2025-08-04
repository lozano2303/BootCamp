package bootCamp.Backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bootCamp.Backend.DTO.ResponseDTO;
import bootCamp.Backend.service.PlayerGameService;

@RestController
@RequestMapping("/playergames")
public class PlayerGameController {

    @Autowired
    private PlayerGameService playerGameService;

    @PostMapping("/assign/{gameId}")
    public ResponseDTO assignPlayersToGame(
            @PathVariable int gameId, 
            @RequestBody List<Integer> playerIds) {
        return playerGameService.assignPlayersToGame(gameId, playerIds);
    }

    @PutMapping("/scores/{gameId}")
    public ResponseDTO updatePlayerScores(
            @PathVariable int gameId, 
            @RequestBody Map<Integer, Integer> playerScores) {
        return playerGameService.updatePlayerScores(gameId, playerScores);
    }
    @GetMapping("/list/{gameId}")
public List<Map<String, Object>> getPlayersByGame(@PathVariable int gameId) {
    return playerGameService.getPlayersByGame(gameId);
}

}