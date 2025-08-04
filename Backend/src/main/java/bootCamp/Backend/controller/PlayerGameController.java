package bootCamp.Backend.controller;

import bootCamp.Backend.DTO.ResponseDTO;
import bootCamp.Backend.service.PlayerGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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