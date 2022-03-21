package com.cricketgame.models;

import com.cricketgame.models.enums.Teams;

import java.util.ArrayList;

public class Team {


    private Teams name;
    private ArrayList<Player> players = new ArrayList<>();


    public Teams getName() {
        return name;
    }

    public void setName(Teams name) {
        this.name = name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


}
