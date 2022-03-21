package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MatchRepository {

    @Autowired
    DatabaseConnector databaseConnector;

    /**
     * Insert overs in the table and get matchId.
     *
     * @param overs
     * @return
     * @throws SQLException
     */
    public int createMatch(int overs) throws SQLException {
        String query = "INSERT INTO MATCHDETAILS(totalovers) VALUES(" + overs + ")";
        ResultSet result = databaseConnector.insertData(query);
        result.next();
        return result.getInt(1);
    }

    /**
     * Get number of overs corresponding to given matchId.
     *
     * @param matchId
     * @return
     */
    public int getOvers(int matchId) throws SQLException {
        String query = "SELECT * FROM MATCHDETAILS WHERE MATCHID = " + matchId;
        ResultSet result = databaseConnector.getResult(query);
        result.next();
        return result.getInt(2);
    }
}