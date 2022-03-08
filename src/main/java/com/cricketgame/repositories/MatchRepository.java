package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MatchRepository {
    public int matchId;
    public void createMatch(int overs) throws SQLException {

        String query = "INSERT INTO MATCHDETAILS(totalovers) VALUES(" + overs + ")";
        ResultSet result = DatabaseService.insertData(query);
        result.next();
        matchId = result.getInt(1);

    }

}