package com.cricketgame;

import com.cricketgame.models.Match;
import com.cricketgame.models.Team;
import com.cricketgame.service.MatchService;

import java.util.Scanner;

class CricketGame {
    public static void main(String[] args) {

        int overs;
        String team1Name, team2Name;
        int choice;
        Scanner scan = new Scanner(System.in);

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

        Match m = new Match.getMatchDetails().setTeam1(team1).setTeam2(team2).setOvers(overs).build();

        MatchService match = new MatchService();
        match.start(m);

        match.getResults(m);

    }
}
