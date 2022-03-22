package com.cricketgame.models.databasemodels.Match;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MatchDetails {
    private int matchId;
    private int overs;
    private ArrayList<TeamDetails> teamDetails;
}
