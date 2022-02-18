package com.cricketgame.models;

import com.cricketgame.Constants;
import com.cricketgame.utils.MatchUtils;

import java.util.ArrayList;



public class Team {
    public Teams name;
    public ArrayList <Player> players = new ArrayList<>();

    public Team(String name) {
        if(MatchUtils.checkValidTeamName(name)){

            this.name = Teams.valueOf(name);
            int ballers = 0;

            for (int i = 0; i < 11; i++) {
                if(ballers <= Constants.MAX_NUMBER_OF_BALLERS) {
                    players.add(new Player("player " + (i + 1), MatchUtils.getRandomNumber(5, 10), PlayerType.BALLER));
                }
                else {
                    players.add(new Player("player " + (i + 1), MatchUtils.getRandomNumber(5, 10), PlayerType.BATSMAN));
                }
                ballers += 1;
            }

        }
        else{
            System.out.println("Team name does not exist.");
            System.exit(0);
        }
    }

}
