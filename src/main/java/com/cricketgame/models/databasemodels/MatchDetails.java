package com.cricketgame.models.databasemodels;

import java.util.ArrayList;

public class MatchDetails {
    int matchId;
    int overs;
    ArrayList<TeamDetails> teamDetails;

    public int getOvers() {
        return overs;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public ArrayList<TeamDetails> getTeamDetails() {
        return teamDetails;
    }

    public void setTeamDetails(ArrayList<TeamDetails> teamDetails) {
        this.teamDetails = teamDetails;
    }
}
