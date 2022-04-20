package com.cricketgame.repositories.mongorepository;

import com.cricketgame.models.entity.BallDetails;
import com.cricketgame.models.entity.PlayerDetails;
import com.cricketgame.repositories.PlayerDetailRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("mongoPlayerRepo")
public interface PlayerDetailsRepository extends MongoRepository<PlayerDetails, Integer>, PlayerDetailRepo {
    @Query("{ teamId : ?0 }")
    List<PlayerDetails> findByTeamId(int teamId);
    @Query("{ playerId : ?0 }")
    PlayerDetails findByPlayerId(int playerId);

}
