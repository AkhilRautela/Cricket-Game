package com.cricketgame.models;

import java.util.ArrayList;

public class Inning {
    Team battingTeam;
    Team bowlingTeam;
    int totalOvers;
    ArrayList<Over> overs;
    boolean firstInningDone;
    int oppositeTeamScore;

    public Inning(Team battingTeam, Team bowlingTeam, int overs, boolean firstInningDone, int oppositeTeamScore) {

        this.totalOvers = overs;
        this.bowlingTeam = bowlingTeam;
        this.battingTeam = battingTeam;
        this.firstInningDone = firstInningDone;
        this.oppositeTeamScore = oppositeTeamScore;
        this.overs = new ArrayList<Over>();

    }


    public Team getBattingTeam() {
        return battingTeam;
    }

    public void setBattingTeam(Team battingTeam) {
        this.battingTeam = battingTeam;
    }

    public Team getBowlingTeam() {
        return bowlingTeam;
    }

    public void setBowlingTeam(Team bowlingTeam) {
        this.bowlingTeam = bowlingTeam;
    }

    public int getTotalOvers() {
        return totalOvers;
    }

    public void setTotalOvers(int totalOvers) {
        this.totalOvers = totalOvers;
    }

    public ArrayList<Over> getOvers() {
        return overs;
    }

    public void setOvers(ArrayList<Over> overs) {
        this.overs = overs;
    }

    public boolean isFirstInningDone() {
        return firstInningDone;
    }

    public void setFirstInningDone(boolean firstInningDone) {
        this.firstInningDone = firstInningDone;
    }

    public int getOppositeTeamScore() {
        return oppositeTeamScore;
    }

    public void setOppositeTeamScore(int oppositeTeamScore) {
        this.oppositeTeamScore = oppositeTeamScore;
    }
}


