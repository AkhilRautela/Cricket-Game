package com.cricketgame.models.databasemodels.Match;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamDetails {
    private String teamName;
    private int totalScore;
    private int totalWicketTaken;
    private ArrayList<PlayerDetails> playerDetails;
}
