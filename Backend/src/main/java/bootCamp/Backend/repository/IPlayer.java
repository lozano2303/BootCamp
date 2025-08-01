package bootCamp.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bootCamp.Backend.model.Player;

public interface IPlayer extends JpaRepository<Player, Integer>{

}
