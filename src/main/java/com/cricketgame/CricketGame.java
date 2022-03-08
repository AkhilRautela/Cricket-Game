package com.cricketgame;

import com.cricketgame.database.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;
import java.util.Scanner;

@SpringBootApplication
@ComponentScan("com.cricketgame")
class CricketGame {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(CricketGame.class,args);
    }

}
