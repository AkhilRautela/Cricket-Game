package com.cricketgame.database;

import com.cricketgame.models.Ball;
import com.cricketgame.models.Player;
import com.cricketgame.models.Team;
import com.cricketgame.models.enums.PlayerType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseUtils {

    public static int getTeamId(String name) throws SQLException {
        String query = "Select * from teamdetails where name = '" + name +"'";
        ResultSet result = DatabaseService.getResult(query);
        result.next();
        return result.getInt(1);
    }

    public static int getPlayerId(int teamid , String name) throws SQLException {
        String query = "SELECT * FROM PLAYERDETAILS WHERE TEAMID = " + teamid + " AND NAME = '" + name +"'";
//        System.out.println(query);
        ResultSet result = DatabaseService.getResult(query);
        result.next();
        return result.getInt(1);
    }

    public static Player createPlayer(int playerId) throws SQLException {
        String query = "SELECT * FROM PLAYERDETAILS WHERE PLAYERID = " + playerId;
        ResultSet result = DatabaseService.getResult(query);
        result.next();
        return new Player(result.getString(2),result.getInt(3), PlayerType.valueOf(result.getString(4)));
    }

    public static Team createTeam(int teamid) throws SQLException {
        String query = "SELECT * FROM TEAMDETAILS WHERE TEAMID = " + teamid;
        ResultSet result = DatabaseService.getResult(query);
        result.next();
        Team team = new Team(result.getString(2));
        return team;
    }

}
