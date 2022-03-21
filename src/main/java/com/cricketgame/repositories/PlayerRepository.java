package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseConnector;
import com.cricketgame.models.Player;
import com.cricketgame.models.Team;
import com.cricketgame.models.enums.PlayerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PlayerRepository {

    @Autowired
    DatabaseConnector databaseConnector;
    @Autowired
    TeamRepository teamRepository;

    /**
     * Get player Id of an player with given teamid and name
     *
     * @param teamid
     * @param name
     * @return
     * @throws SQLException
     */
    public int getPlayerId(int teamid, String name) throws SQLException {
        String query = "SELECT * FROM PLAYERDETAILS WHERE TEAMID = " + teamid + " AND NAME = '" + name + "'";
        ResultSet result = databaseConnector.getResult(query);
        result.next();
        return result.getInt(1);
    }

    /**
     * Create player object for the corresponding playerId.
     *
     * @param playerId
     * @return
     * @throws SQLException
     */
    public Player createPlayer(int playerId) throws SQLException {
        String query = "SELECT * FROM PLAYERDETAILS WHERE PLAYERID = " + playerId;
        ResultSet result = databaseConnector.getResult(query);
        result.next();
        return new Player(result.getString(2), result.getInt(3), PlayerType.valueOf(result.getString(4)));
    }

    /**
     * Check If the player is present in the given team or not.
     *
     * @param team
     * @param playerId
     * @return
     * @throws SQLException
     */
    public boolean isPlayerPresent(Team team, int playerId) throws SQLException {
        int teamId = teamRepository.getTeamId(String.valueOf(team.getName()));
        String query = "SELECT * FROM PLAYERDETAILS WHERE teamid = " + teamId + " and playerid = " + playerId;
        ResultSet result = databaseConnector.getResult(query);
        return result.next();
    }

}
