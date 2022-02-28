package com.cricketgame;

import com.cricketgame.Database.DataInsertion;
import com.cricketgame.models.Team;
import com.cricketgame.Database.DatabaseService;
import com.cricketgame.service.MatchService;

import java.sql.SQLException;
import java.util.Scanner;

class CricketGame {
    public static void main(String[] args) {


        int overs;
        String team1Name, team2Name;
        int choice;
        Scanner scan = new Scanner(System.in);


        try {
            DatabaseService.getInstance().createConnection();

//        DataInsertion.InsertPlayersInDatabase(); For Creating players for a team .
//        DataInsertion.InsertTeamInDatabase();  For inserting teams in the database from the team enum.

        } catch (SQLException e) {
            e.printStackTrace();
        }






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
}
