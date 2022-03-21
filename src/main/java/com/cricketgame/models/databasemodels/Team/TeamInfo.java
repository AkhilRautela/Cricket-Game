package com.cricketgame.models.databasemodels.Team;

import java.util.ArrayList;

public class TeamInfo {

    String name;
    int id;
    ArrayList<PlayerInfo> playerInfos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<PlayerInfo> getPlayerInfos() {
        return playerInfos;
    }

    public void setPlayerInfos(ArrayList<PlayerInfo> playerInfos) {
        this.playerInfos = playerInfos;
    }

}
