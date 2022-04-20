package com.cricketgame.repositories.mongorepository;

import com.cricketgame.models.entity.InningDetails;
import com.cricketgame.models.entity.OverDetails;
import com.cricketgame.repositories.OverDetailRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mongoOverRepo")
public interface OverDetailsRepository extends MongoRepository<OverDetails, Integer>, OverDetailRepo {
    @Query("{bowlerId : ?0 , $or : [ {inningId : ?1} , {inningId : ?2}] }")
    List<OverDetails> findOversDoneByThePlayer(int bowlerId, int inning1Id, int inning2id);
}
