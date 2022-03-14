package com.cricketgame.models.databasemodels.Match;

import java.util.ArrayList;

public class TeamDetails {
    String teamName;
    int totalScore;
    int totalWicketTaken;
    ArrayList<PlayerDetails> playerDetails;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalWicketTaken() {
        return totalWicketTaken;
    }

    public void setTotalWicketTaken(int totalWicketTaken) {
        this.totalWicketTaken = totalWicketTaken;
    }

    public ArrayList<PlayerDetails> getPlayerDetails() {
        return playerDetails;
    }

    public void setPlayerDetails(ArrayList<PlayerDetails> playerDetails) {
        this.playerDetails = playerDetails;
    }

}
