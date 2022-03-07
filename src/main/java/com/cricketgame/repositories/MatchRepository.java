package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchRepository {
    public static int matchId;

    public static void createMatch(int overs) throws SQLException {

        String query = "INSERT INTO MATCHDETAILS(totalovers) VALUES(" + overs + ")";
        ResultSet result = DatabaseService.insertData(query);
        result.next();
        matchId = result.getInt(1);

    }

}