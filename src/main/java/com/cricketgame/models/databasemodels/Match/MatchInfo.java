package com.cricketgame.models.databasemodels.Match;

import lombok.Data;

@Data
public class MatchInfo {
    private String team1Name;
    private String team2Name;
    private int overs;
}
