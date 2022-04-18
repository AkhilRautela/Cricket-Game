package com.cricketgame.repositories.mongorepository;

import com.cricketgame.models.entity.BallDetails;
import com.cricketgame.repositories.BallDetailRepo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository("mongoBallRepo")
public interface BallDetailsRepository extends MongoRepository<BallDetails, Integer>, BallDetailRepo {

    @Query(value = "{ strikerId : ?0 , $or : [ {inningId : ?1} , {inningId : ?2}] }")
    ArrayList<BallDetails> getBallsPlayed(int playerId, int inningId, int inningId1);

    @Query("{ overId : ?0 }")
    ArrayList<BallDetails> getBallsForTheOver(int overId);

}
