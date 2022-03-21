package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseConnector;
import com.cricketgame.models.Player;
import com.cricketgame.models.Team;
import com.cricketgame.models.enums.PlayerType;
import com.cricketgame.models.enums.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class TeamRepository {

    @Autowired
    DatabaseConnector databaseConnector;

    /**
     * Get all the players in the given team
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public ArrayList<Player> getPlayersInTeam(String name) throws SQLException {

        int teamid = getTeamId(name);
        ArrayList<Player> players = new ArrayList<Player>();
        String query = "SELECT * FROM PLAYERDETAILS WHERE TEAMID = " + teamid;
        ResultSet playerDetails = databaseConnector.getResult(query);

        while (playerDetails.next()) {
            players.add(new Player(playerDetails.getString(2), playerDetails.getInt(3), PlayerType.valueOf(playerDetails.getString(4))));
        }

        return players;
    }

    /**
     * Get team Id corresponding to the name of the team.
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public int getTeamId(String name) throws SQLException {
        String query = "Select * from teamdetails where name = '" + name + "'";
        ResultSet result = databaseConnector.getResult(query);
        if (result.next() == false) return -1;
        return result.getInt(1);
    }

    /**
     * create Team object for provided teamId.
     *
     * @param teamid
     * @return
     * @throws SQLException
     */
    public Team createTeam(int teamid) throws SQLException {
        String query = "SELECT * FROM TEAMDETAILS WHERE TEAMID = " + teamid;
        ResultSet result = databaseConnector.getResult(query);
        result.next();
        Team team = new Team();
        String teamName = result.getString(2);
        team.setName(Teams.valueOf(teamName));
        team.setPlayers(getPlayersInTeam(teamName));
        return team;
    }

}
