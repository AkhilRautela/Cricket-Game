package com.cricketgame.repositories.mongorepository;

import com.cricketgame.models.entity.MatchDetails;
import com.cricketgame.repositories.MatchDetailRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("mongoMatchRepo")
public interface MatchDetailsRepository extends MongoRepository<MatchDetails, Integer>, MatchDetailRepo {

}
