package com.cricketgame.repositories.mongorepository;

import com.cricketgame.models.entity.MatchDetails;
import com.cricketgame.models.entity.TeamDetails;
import com.cricketgame.repositories.TeamDetailRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository("mongoTeamRepo")
public interface TeamDetailsRepository extends MongoRepository<TeamDetails, Integer>, TeamDetailRepo {
    @Query("{ name : '?0' }")
    TeamDetails findByName(String name);
    @Query("{ teamId : ?0 }")
    TeamDetails findByTeamId(int teamId);
}
