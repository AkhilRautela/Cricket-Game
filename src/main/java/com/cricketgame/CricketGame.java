package com.cricketgame;

import com.cricketgame.database.DataInsertion;
import com.cricketgame.models.Team;
import com.cricketgame.database.DatabaseService;
import com.cricketgame.service.DataFetchService;
import com.cricketgame.service.MatchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Scanner;

@SpringBootApplication
class CricketGame {

    static Scanner scan;

    public static void main(String[] args) throws SQLException {

        SpringApplication.run(CricketGame.class,args);

        scan = new Scanner(System.in);
        int choice;

        startDatabaseConnection(); // Starting Connection to the database

        System.out.println("Enter 1 for getting details of Previous Match \nEnter 2 for play an New Game");
        choice = scan.nextInt();

        switch (choice){
            case 1:
                handleFetchMatchDetails();
                break;
            case 2:
                handleNewMatch();
                break;
            default:
                System.out.println("Invalid Option");
                System.exit(0);
        }

    }

    private static void handleFetchMatchDetails() throws SQLException {
        int matchId;
        System.out.println("Enter The MatchId you want to retrieve the match");
        matchId = scan.nextInt();
        DataFetchService dataFetchService = new DataFetchService();
        dataFetchService.getMatch(matchId);
        dataFetchService.getResults();
    }

    private static void handleNewMatch() throws SQLException {
        int overs;
        int choice;
        String team1Name, team2Name;

        System.out.println("Enter the type of Game");
        System.out.println("1 -> For ODI \t 2-> For T20");
        choice = scan.nextInt();

        switch (choice) {
            case 1:
                overs = 5; // Less overs as it may takes to much time to play.
                break;
            case 2:
                overs = 2;
                break;
            default:
                overs = 1;
        }

        // Teams available in enums are INDIA PAKISTAN AUSTRALIA AND ENGLAND
        System.out.println("Enter Team1 Name");
        team1Name = scan.next().toUpperCase();
        Team team1 = new Team(team1Name);

        System.out.println("Enter Team2 Name");
        team2Name = scan.next().toUpperCase();
        Team team2 = new Team(team2Name);

        MatchService match = new MatchService();
        match.start(team1,team2,overs);
        match.showScoreBoard();
        match.getResults();
    }

    public static void startDatabaseConnection(){
        try {
            DatabaseService.getInstance().createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
