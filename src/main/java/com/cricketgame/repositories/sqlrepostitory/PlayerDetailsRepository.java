package com.cricketgame.repositories.sqlrepostitory;

import com.cricketgame.models.entity.BallDetails;
import com.cricketgame.models.entity.PlayerDetails;
import com.cricketgame.repositories.PlayerDetailRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("sqlPlayerRepo")
public interface PlayerDetailsRepository extends JpaRepository<PlayerDetails, Integer>, PlayerDetailRepo {
    List<PlayerDetails> findByTeamId(int teamId);
    PlayerDetails findByPlayerId(int playerId);
}
