package com.cricketgame.models;

import com.cricketgame.Constants;
import com.cricketgame.utils.MatchUtils;

import java.util.ArrayList;



public class Team {

    private Teams name;
    private ArrayList <Player> players = new ArrayList<>();

    public Team(String name) {
        if(MatchUtils.checkValidTeamName(name)){

            this.name = Teams.valueOf(name);
            int ballers = 0;

            for (int i = 0; i < 11; i++) {
                if(ballers > Constants.MAX_NUMBER_OF_BALLERS) {
                    players.add(new Player("player " + (i + 1), MatchUtils.getRandomNumber(7, 10), PlayerType.BOWLER));
                }
                else {
                    players.add(new Player("player " + (i + 1), MatchUtils.getRandomNumber(7, 10), PlayerType.BATSMAN));
                }
                ballers += 1;
            }

        }
        else{
            System.out.println("Team name does not exist.");
            System.exit(0);
        }
    }

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
