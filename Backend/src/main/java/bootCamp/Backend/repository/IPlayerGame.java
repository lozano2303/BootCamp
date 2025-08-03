package bootCamp.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import bootCamp.Backend.model.PlayerGame;
import bootCamp.Backend.model.ids.Player_GameID;

public interface IPlayerGame extends JpaRepository<PlayerGame, Player_GameID> {

    // Consulta usando el ID compuesto (forma más directa)
    @Query("SELECT COUNT(pg) > 0 FROM PlayerGame pg WHERE pg.id.gameID = :gameId AND pg.id.playerID = :playerId")
    boolean existsPlayerInGame(@Param("gameId") int gameId, @Param("playerId") int playerId);

    // Consulta consistente usando el ID compuesto
    @Query("SELECT pg FROM PlayerGame pg WHERE pg.id.gameID = :gameId")
    List<PlayerGame> findByGameId(@Param("gameId") int gameId);
    
    // Consulta para el ganador (también usando ID compuesto)
    @Query("SELECT pg FROM PlayerGame pg WHERE pg.id.gameID = :gameId ORDER BY pg.score DESC")
    List<PlayerGame> findWinner(@Param("gameId") int gameId);
}