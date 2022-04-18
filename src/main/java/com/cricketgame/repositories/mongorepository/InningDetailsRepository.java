package com.cricketgame.repositories.mongorepository;

import com.cricketgame.models.entity.InningDetails;
import com.cricketgame.repositories.InningDetailRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("mongoInningRepo")
public interface InningDetailsRepository extends MongoRepository<InningDetails, Integer>, InningDetailRepo {
    List<InningDetails> findByMatchId(int id);
}
