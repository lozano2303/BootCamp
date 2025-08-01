package bootCamp.Backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import bootCamp.Backend.model.Game;

public interface IGame extends JpaRepository<Game, Integer>{

}
