package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseService;
import com.cricketgame.database.DatabaseUtils;
import com.cricketgame.models.Player;
import com.cricketgame.models.enums.PlayerType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeamRepository {

    public static ArrayList<Player> getPlayersInTeam(String name) throws SQLException {

        int teamid = DatabaseUtils.getTeamId(name);
        ArrayList <Player> players = new ArrayList<Player>();
        String query = "SELECT * FROM PLAYERDETAILS WHERE TEAMID = " + teamid;
        ResultSet playerDetails = DatabaseService.getResult(query);

        while(playerDetails.next()){
            players.add(new Player(playerDetails.getString(2),playerDetails.getInt(3), PlayerType.valueOf(playerDetails.getString(4))));
        }

        return players;
    }
}
