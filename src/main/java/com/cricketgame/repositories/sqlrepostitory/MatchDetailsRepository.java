package com.cricketgame.repositories.sqlrepostitory;

import com.cricketgame.models.entity.MatchDetails;
import com.cricketgame.repositories.MatchDetailRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("sqlMatchRepo")
public interface MatchDetailsRepository extends JpaRepository<MatchDetails, Integer> , MatchDetailRepo {

}
