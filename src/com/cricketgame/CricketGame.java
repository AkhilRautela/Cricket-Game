package com.cricketgame;

import java.util.Scanner;

public class CricketGame {
    public static void main(String[] args) {

        int Overs;
        String Team1Name, Team2Name;
        Scanner Scan = new Scanner(System.in);

        System.out.println("Enter number of overs");
        Overs = Scan.nextInt();

        System.out.println("Enter Team1 Name");
        Team1Name = Scan.next();
        System.out.println("Enter Team2 Name");
        Team2Name = Scan.next();

        Team Team1 = new Team(Team1Name);
        Team Team2 = new Team(Team2Name);

        Match m = new Match.getMatchDetails().SetTeam1(Team1).SetTeam2(Team2).SetOvers(Overs).build();
        m.startMatch();
        m.getResults();

    }
}
