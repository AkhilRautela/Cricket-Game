package com.cricketgame.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    public static int getTeamId(String name) throws SQLException {
        String query = "Select * from teamdetails where name = '" + name +"'";
        ResultSet result = DatabaseService.getResult(query);
        result.next();
        return result.getInt(1);
    }

    public static int getPlayerId(int teamid , String name) throws SQLException {
        String query = "SELECT * FROM PLAYERDETAILS WHERE TEAMID = " + teamid + " AND NAME = '" + name +"'";
        System.out.println(query);
        ResultSet result = DatabaseService.getResult(query);
        result.next();
        return result.getInt(1);
    }

}
