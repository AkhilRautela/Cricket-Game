package com.cricketgame.models;

import com.cricketgame.models.enums.Teams;
import com.cricketgame.repositories.TeamRepository;
import com.cricketgame.utils.MatchUtils;

import java.util.ArrayList;



public class Team {

    private Teams name;
    private ArrayList <Player> players = new ArrayList<>();

    public Team(String name) {
        if(MatchUtils.checkValidTeamName(name)){
            this.name = Teams.valueOf(name);
            try {
                this.players = TeamRepository.getPlayersInTeam(name);
            }
            catch (Exception e){
                e.printStackTrace();
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
