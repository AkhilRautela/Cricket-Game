package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseServiceImpl;
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
    DatabaseServiceImpl databaseService;

    public ArrayList<Player> getPlayersInTeam(String name) throws SQLException {

        int teamid = getTeamId(name);
        ArrayList <Player> players = new ArrayList<Player>();
        String query = "SELECT * FROM PLAYERDETAILS WHERE TEAMID = " + teamid;
        ResultSet playerDetails = databaseService.getResult(query);

        while(playerDetails.next()){
            players.add(new Player(playerDetails.getString(2),playerDetails.getInt(3), PlayerType.valueOf(playerDetails.getString(4))));
        }

        return players;
    }

    public int getTeamId(String name) throws SQLException {
        String query = "Select * from teamdetails where name = '" + name + "'";
        ResultSet result = databaseService.getResult(query);
        if(result.next() == false) return -1;
        return result.getInt(1);
    }


    public Team createTeam(int teamid) throws SQLException {
        String query = "SELECT * FROM TEAMDETAILS WHERE TEAMID = " + teamid;
        ResultSet result = databaseService.getResult(query);
        result.next();
        Team team = new Team();
        String teamName = result.getString(2);
        team.setName(Teams.valueOf(teamName));
        team.setPlayers(getPlayersInTeam(teamName));
        return team;
    }

}
