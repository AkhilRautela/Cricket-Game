package com.cricketgame;

public class Team{
    String name;
    int score;
    int wickets;
    Boolean havePlayed;
    Player currentBatsman;
    Player nonStriker;
    Player[] players;

    Team(String name) {
        this.name = name;
        this.score = 0;
        this.wickets = 0;
    }

    void getStats() {
        System.out.println("\nStats for " + name);
        System.out.println("Name \t Wickets \t Runs");
        for (Player p : players) {
            if (p == currentBatsman || p == nonStriker) {
                System.out.println("* " + p.name + "\t" + p.wicket + "\t\t" + p.score);
            } else {
                System.out.println(p.name + "  \t" + p.wicket + "\t\t" + p.score);
            }

        }
    }

    void getPlayers() {
        //fetch Players from database
        players = new Player[11];

        for (int i = 0; i < 11; i++) {
            players[i] = new Player("player " + (i + 1), 10, 10, 10);
        }

    }


}
