package com.cricketgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
class CricketGame {

    public static void main(String[] args) {
        SpringApplication.run(CricketGame.class, args);
    }

}
