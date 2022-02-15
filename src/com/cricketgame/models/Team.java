package com.cricketgame.models;

import com.cricketgame.utils.MatchUtils;

import java.util.ArrayList;



public class Team {
    public Teams name;
    public int score;
    public int wickets;
    public ArrayList <Player> players = new ArrayList<>();
    public Player nonStriker;
    public Player batsman;
    public boolean havePlayed = false;

    public Team(String name) {
        if(MatchUtils.checkValidTeamName(name)){
            this.name = Teams.valueOf(name);
            this.score = 0;
            this.wickets = 0;
        }
        else{
            System.out.println("Team name does not exist.");
            System.exit(0);
        }
    }

    public void getPlayers() {
        //fetch Players from database
        for (int i = 0; i < 11; i++) {
            players.add(new Player("player " + (i + 1),
                    (int) (Math.random()) * 10,
                    (int) (Math.random()) * 10,
                    (int) (Math.random()) * 10)
            );
        }

    }


}
