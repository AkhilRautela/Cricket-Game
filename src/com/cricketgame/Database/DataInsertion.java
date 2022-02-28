package com.cricketgame.Database;

import com.cricketgame.models.enums.PlayerType;
import com.cricketgame.models.enums.Teams;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataInsertion {

    public static void InsertTeamInDatabase(){
        for(Teams name : Teams.values()){
            String query = "Insert into teamdetails(name) value('" + name +"')";
            try  {
                DatabaseService.insertData(query);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void InsertPlayersInDatabase() throws SQLException {
        for(Teams name : Teams.values()){
            int cnt = 1;
            for(int i = 0; i < 5; i++){
                String query = "INSERT INTO PLAYERDETAILS(name,rating,playertype) VALUES('player " + cnt +"',10,'"+ PlayerType.BOWLER.toString() +"')";
//                System.out.println(query);
                ResultSet resultSet = DatabaseService.insertData(query);
                resultSet.next();
                String relationQuery = "INSERT INTO TEAM VALUES(" + DatabaseUtils.getTeamId(name.toString()) + "," + resultSet.getInt(1) + ")";
                DatabaseService.insertData(relationQuery);
                cnt++;
            }
            for(int j = 0; j <= 5; j++){
                String query = "INSERT INTO PLAYERDETAILS(name,rating,playertype) VALUES('player " + cnt +"',10,'"+ PlayerType.BATSMAN.toString() +"')";
                ResultSet resultSet = DatabaseService.insertData(query);
                resultSet.next();
                String relationQuery = "INSERT INTO TEAM VALUES(" + DatabaseUtils.getTeamId(name.toString()) + "," + resultSet.getInt(1) + ")";
                DatabaseService.insertData(relationQuery);
                cnt++;
            }

        }
    }


}
