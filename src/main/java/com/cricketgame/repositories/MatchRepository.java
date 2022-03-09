package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MatchRepository {

    public int matchId;
    @Autowired
    DatabaseServiceImpl databaseService;

    public void createMatch(int overs) throws SQLException {

        String query = "INSERT INTO MATCHDETAILS(totalovers) VALUES(" + overs + ")";
        ResultSet result = databaseService.insertData(query);
        result.next();
        matchId = result.getInt(1);

    }

}