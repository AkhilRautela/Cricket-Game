package com.cricketgame.models.request;

import lombok.Data;

@Data
public class MatchInfo {
    String team1Name;
    String team2Name;
    int overs;
}
