package com.cricketgame.models.databasemodels.Team;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamInfo {

    private String name;
    private int id;
    private ArrayList<PlayerInfo> playerInfos;

}
