package com.cricketgame;

import com.cricketgame.database.DatabaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;
import java.util.Scanner;

@SpringBootApplication
@ComponentScan("com.cricketgame")
class CricketGame {

    static Scanner scan;

    public static void main(String[] args) throws SQLException {
        startDatabaseConnection();
        SpringApplication.run(CricketGame.class,args);
    }

    public static void startDatabaseConnection(){
        try {
            DatabaseService.getInstance().createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}