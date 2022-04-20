package com.cricketgame.models.response;

import com.cricketgame.models.entity.TeamDetails;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MatchInfo {
    private int matchId;
    private ArrayList <TeamDetailsInMatch> teamDetailsInMatches;
}
