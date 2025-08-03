package bootCamp.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bootCamp.Backend.model.PlayerGame;
import bootCamp.Backend.model.ids.Player_GameID;

public interface IPlayerGame extends JpaRepository<PlayerGame, Player_GameID> {
    // Additional query methods can be defined here if needed
}
