package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseService;
import com.cricketgame.models.Player;
import com.cricketgame.models.enums.PlayerType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PlayerRepository {

    public int getPlayerId(int teamid , String name) throws SQLException {
        String query = "SELECT * FROM PLAYERDETAILS WHERE TEAMID = " + teamid + " AND NAME = '" + name +"'";
//        System.out.println(query);
        ResultSet result = DatabaseService.getResult(query);
        result.next();
        return result.getInt(1);
    }

    public Player createPlayer(int playerId) throws SQLException {
        String query = "SELECT * FROM PLAYERDETAILS WHERE PLAYERID = " + playerId;
        ResultSet result = DatabaseService.getResult(query);
        result.next();
        return new Player(result.getString(2),result.getInt(3), PlayerType.valueOf(result.getString(4)));
    }

}
