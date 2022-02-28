package com.cricketgame.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    public static int getTeamId(String name) throws SQLException {
        String query = "Select * from teamdetails where name = '" + name +"'";
        ResultSet result = DatabaseService.getResult(query);
        result.next();
        return result.getInt(1);
    }

}
