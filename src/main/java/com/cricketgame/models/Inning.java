package com.cricketgame.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Inning {
    private Team battingTeam;
    private Team bowlingTeam;
    private int totalOvers;
    private ArrayList<Over> overs;
    private boolean firstInningDone;
    private int oppositeTeamScore;

    public Inning(Team battingTeam, Team bowlingTeam, int overs, boolean firstInningDone, int oppositeTeamScore) {
        this.totalOvers = overs;
        this.bowlingTeam = bowlingTeam;
        this.battingTeam = battingTeam;
        this.firstInningDone = firstInningDone;
        this.oppositeTeamScore = oppositeTeamScore;
        this.overs = new ArrayList<Over>();
    }

    public Inning(Team battingTeam, Team bowlingTeam) {
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
    }

}


