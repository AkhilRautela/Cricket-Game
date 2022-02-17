package com.cricketgame.models;

import java.util.ArrayList;

public class Inning {
    public int wickets;
    public int score;
    public Team battingTeam;
    public Team bowlingTeam;
    public int overs;
    public ArrayList<Over> Overs;
    public ArrayList<Integer> scoreOfPlayers;
    public ArrayList<Integer> wicketsTaken;
    public ArrayList<Boolean> isOut;

    public Inning(Team battingTeam, Team bowlingTeam, int overs) {

        this.overs = overs;
        this.bowlingTeam = bowlingTeam;
        this.battingTeam = battingTeam;
        this.score = 0;
        this.wickets = 0;
        this.scoreOfPlayers = new ArrayList<Integer>();
        this.wicketsTaken = new ArrayList<Integer>();
        this.isOut = new ArrayList<Boolean>();
        this.Overs = new ArrayList<Over>();

        for (int i = 0; i < 11; i++) {
            this.scoreOfPlayers.add(0);
            this.wicketsTaken.add(0);
            this.isOut.add(false);
        }
    }
}
