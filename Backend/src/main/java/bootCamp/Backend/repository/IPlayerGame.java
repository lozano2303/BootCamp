package bootCamp.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bootCamp.Backend.model.PlayerGame;

public interface IPlayerGame extends JpaRepository<PlayerGame, Integer>{

}
