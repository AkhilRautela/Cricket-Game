package com.cricketgame.config;

import com.cricketgame.models.entity.BallDetails;
import com.cricketgame.models.entity.InningDetails;
import com.cricketgame.repositories.*;
import com.cricketgame.repositories.mongorepository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;


@ConditionalOnProperty(
        value = "database.type",
        havingValue = "mongo"
)
@Configuration
public class BeanConfigurationMongo {

    @Autowired
    @Qualifier("mongoBallRepo")
    BallDetailsRepository ballDetailsRepository;

    @Autowired
    @Qualifier("mongoInningRepo")
    InningDetailsRepository inningDetailsRepository;

    @Autowired
    @Qualifier("mongoMatchRepo")
    MatchDetailsRepository matchDetailsRepository;

    @Autowired
    @Qualifier("mongoOverRepo")
    OverDetailsRepository overDetailsRepository;

    @Autowired
    @Qualifier("mongoPlayerRepo")
    PlayerDetailsRepository playerDetailsRepository;

    @Autowired
    @Qualifier("mongoTeamRepo")
    TeamDetailsRepository teamDetailsRepository;

    @Bean
    @Primary
    BallDetailRepo ballDetailRepo(){
        return ballDetailsRepository;
    }

    @Bean
    @Primary
    InningDetailRepo inningDetailRepo(){ return inningDetailsRepository; }

    @Bean
    @Primary
    MatchDetailRepo matchDetailRepo(){ return  matchDetailsRepository; }

    @Bean
    @Primary
    OverDetailRepo overDetailsRepo(){ return overDetailsRepository;}

    @Bean
    @Primary
    PlayerDetailRepo playerDetailRepo(){return playerDetailsRepository;}

    @Bean
    @Primary
    TeamDetailRepo teamDetailsRepo(){return teamDetailsRepository;}




}
