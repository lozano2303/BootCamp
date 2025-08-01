package bootCamp.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import bootCamp.Backend.model.Card;

@EnableJpaRepositories
public interface ICard extends JpaRepository<Card, Integer>{

}
