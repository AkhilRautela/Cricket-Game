package com.cricketgame.models;

import com.cricketgame.utils.MatchUtils;

import java.util.ArrayList;



public class Team {
    public Teams name;
    public ArrayList <Player> players = new ArrayList<>();

    public Team(String name) {
        if(MatchUtils.checkValidTeamName(name)){
            this.name = Teams.valueOf(name);
            int cnt = 0;
            for (int i = 0; i < 11; i++) {
                players.add(new Player("player " + (i + 1), MatchUtils.getRandomNumber(5,10)));
                if(cnt <= 5) {
                    players.get(i).playertype = PlayerType.BALLER;
                }
                else {
                    players.get(i).playertype = PlayerType.BATSMAN;
                }
                cnt += 1;
            }
        }
        else{
            System.out.println("Team name does not exist.");
            System.exit(0);
        }
    }

}
