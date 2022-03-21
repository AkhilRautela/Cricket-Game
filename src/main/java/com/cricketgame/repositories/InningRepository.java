package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseConnector;
import com.cricketgame.models.Inning;
import com.cricketgame.models.Over;
import com.cricketgame.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class InningRepository {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    DatabaseConnector databaseConnector;
    @Autowired
    OverRepository overRepository;

    /**
     * Insert inning details and returns the inning id corresponding it.
     *
     * @param matchId
     * @param battingTeamId
     * @param bowlingTeamId
     * @return
     * @throws SQLException
     */
    public int insertInning(int matchId, int battingTeamId, int bowlingTeamId) throws SQLException {
        String query = "INSERT INTO INNINGDETAILS(battingteam,bowlingteam,matchid) values(" + battingTeamId + "," + bowlingTeamId + "," + matchId + ")";
        ResultSet result = databaseConnector.insertData(query);
        result.next();
        return result.getInt(1);
    }

    /**
     * Create inning object corresponding to the given inningID.
     *
     * @param inningId
     * @return
     * @throws SQLException
     */
    public Inning createInnings(int inningId) throws SQLException {
        String query = "SELECT * FROM INNINGDETAILS WHERE INNINGID = " + inningId;
        ResultSet resultSet = databaseConnector.getResult(query);
        resultSet.next();
        Team battingTeam = teamRepository.createTeam(resultSet.getInt(2));
        Team bowlingTeam = teamRepository.createTeam(resultSet.getInt(3));
        Inning inning = new Inning(battingTeam, bowlingTeam);
        ArrayList<Over> overs = overRepository.createOvers(inningId);
        inning.setOvers(overs);
        return inning;
    }

    /**
     * Get Innings for the current matchID.
     *
     * @param matchId
     * @return
     * @throws SQLException
     */
    public ArrayList<Integer> getInnings(int matchId) throws SQLException {

        String query = "SELECT * FROM INNINGDETAILS WHERE MATCHID =" + matchId;
        ResultSet result = databaseConnector.getResult(query);
        ArrayList<Integer> inningsIds = new ArrayList<Integer>();
        while (result.next()) {
            inningsIds.add(result.getInt(1));
        }
        return inningsIds;
    }
}
