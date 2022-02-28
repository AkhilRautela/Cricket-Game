package com.cricketgame.models;

import com.cricketgame.Constants;
import com.cricketgame.Database.DatabaseService;
import com.cricketgame.Database.DatabaseUtils;
import com.cricketgame.models.enums.PlayerType;
import com.cricketgame.models.enums.Teams;
import com.cricketgame.utils.MatchUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class Team {

    private Teams name;
    private ArrayList <Player> players = new ArrayList<>();

    public Team(String name) {
        if(MatchUtils.checkValidTeamName(name)){

            this.name = Teams.valueOf(name);
            try {
                int teamid = DatabaseUtils.getTeamId(name);
                getPlayersInTeam(teamid);
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

    private void getPlayersInTeam(int teamid) throws SQLException {

        String query = "SELECT * FROM TEAM WHERE TEAMID = " + teamid;
        ResultSet resultSet = DatabaseService.getResult(query);

        while(resultSet.next()){
            int playerId = resultSet.getInt(2);
            String playerQuery = "SELECT * FROM PLAYERDETAILS WHERE PLAYERID = " + playerId;
            ResultSet playerDetails = DatabaseService.getResult(playerQuery);
            playerDetails.next();
            players.add(new Player(playerDetails.getString(2),playerDetails.getInt(3),PlayerType.valueOf(playerDetails.getString(4))));

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
