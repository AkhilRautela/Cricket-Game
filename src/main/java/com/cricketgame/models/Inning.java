package com.cricketgame.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Inning {
    private Team battingTeam;
    private Team bowlingTeam;
    private int totalOvers;
    private int score;
    private boolean firstInningDone;
    private int oppositeTeamScore;
    private ArrayList<Boolean> isPlayerOut;

    public Inning(Team battingTeam, Team bowlingTeam, int totalOvers, boolean firstInningDone, int oppositeTeamScore) {
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.totalOvers = totalOvers;
        this.firstInningDone = firstInningDone;
        this.oppositeTeamScore = oppositeTeamScore;
        this.isPlayerOut = new ArrayList<>();
        for(int i = 0; i < 11; i++) this.isPlayerOut.add(false);
        this.score = 0;
    }
}


