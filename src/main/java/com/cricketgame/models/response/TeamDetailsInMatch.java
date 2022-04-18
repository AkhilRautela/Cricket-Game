package com.cricketgame.models.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamDetailsInMatch {
    private int teamId;
    private ArrayList <PlayerDetailsInMatch> playerDetailsInMatches;
}
