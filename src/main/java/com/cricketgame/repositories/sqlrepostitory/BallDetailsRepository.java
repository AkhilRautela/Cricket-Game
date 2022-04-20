package com.cricketgame.repositories.sqlrepostitory;

import com.cricketgame.models.entity.BallDetails;
import com.cricketgame.repositories.BallDetailRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository("sqlBallRepo")
public interface BallDetailsRepository extends JpaRepository<BallDetails, Integer> , BallDetailRepo {

    @Query(value = "SELECT * FROM ball_details WHERE striker_id = ?1 AND inning_id IN (?2, ?3)", nativeQuery = true)
    ArrayList<BallDetails> getBallsPlayed(int playerId, int inningId, int inningId1);

    @Query(value = "SELECT * FROM ball_details WHERE over_id = ?1", nativeQuery = true)
    ArrayList<BallDetails> getBallsForTheOver(int overId);

}
