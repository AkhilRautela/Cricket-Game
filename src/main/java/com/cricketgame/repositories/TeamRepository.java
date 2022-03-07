package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseService;
import com.cricketgame.models.Player;
import com.cricketgame.models.Team;
import com.cricketgame.models.enums.PlayerType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeamRepository {

    public static ArrayList<Player> getPlayersInTeam(String name) throws SQLException {

        int teamid = getTeamId(name);
        ArrayList <Player> players = new ArrayList<Player>();
        String query = "SELECT * FROM PLAYERDETAILS WHERE TEAMID = " + teamid;
        ResultSet playerDetails = DatabaseService.getResult(query);

        while(playerDetails.next()){
            players.add(new Player(playerDetails.getString(2),playerDetails.getInt(3), PlayerType.valueOf(playerDetails.getString(4))));
        }

        return players;
    }

    public static int getTeamId(String name) throws SQLException {
        String query = "Select * from teamdetails where name = '" + name +"'";
        ResultSet result = DatabaseService.getResult(query);
        result.next();
        return result.getInt(1);
    }


    public static Team createTeam(int teamid) throws SQLException {
        String query = "SELECT * FROM TEAMDETAILS WHERE TEAMID = " + teamid;
        ResultSet result = DatabaseService.getResult(query);
        result.next();
        Team team = new Team(result.getString(2));
        return team;
    }
}
