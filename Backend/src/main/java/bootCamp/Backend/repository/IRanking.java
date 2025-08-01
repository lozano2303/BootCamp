package bootCamp.Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bootCamp.Backend.model.Ranking;

public interface IRanking extends JpaRepository<Ranking, Integer>{
}
