package com.cricketgame.config;


import com.cricketgame.repositories.*;
import com.cricketgame.repositories.sqlrepostitory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value = "database.type",
        havingValue = "sql",
        matchIfMissing = true
)
@DependsOn("sqlDatabaseCofig")
@Configuration
public class BeanConfigurationSQL {
    @Autowired
    @Qualifier("sqlBallRepo")
    BallDetailsRepository ballDetailsRepository;

    @Autowired
    @Qualifier("sqlInningRepo")
    InningDetailsRepository inningDetailsRepository;

    @Autowired
    @Qualifier("sqlMatchRepo")
    MatchDetailsRepository matchDetailsRepository;

    @Autowired
    @Qualifier("sqlOverRepo")
    OverDetailsRepository overDetailsRepository;

    @Autowired
    @Qualifier("sqlPlayerRepo")
    PlayerDetailsRepository playerDetailsRepository;

    @Autowired
    @Qualifier("sqlTeamRepo")
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
