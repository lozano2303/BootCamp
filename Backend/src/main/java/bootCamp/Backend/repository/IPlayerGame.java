package bootCamp.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import bootCamp.Backend.model.PlayerGame;
import bootCamp.Backend.model.ids.Player_GameID;

public interface IPlayerGame extends JpaRepository<PlayerGame, Player_GameID> {
    
    @Query("SELECT pg FROM player_game pg WHERE pg.gameID.id = :gameId ORDER BY pg.score DESC")
    List<PlayerGame> findWinner(@Param("gameId") int gameId);
}
