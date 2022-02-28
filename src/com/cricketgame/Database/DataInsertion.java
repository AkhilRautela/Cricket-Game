package com.cricketgame.database;

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
            int teamid = DatabaseUtils.getTeamId(name.toString());
            for(int j = 0; j <= 5; j++){
                String query = "INSERT INTO PLAYERDETAILS(name,rating,playertype,teamid) VALUES('player " + cnt +"',10,'"+ PlayerType.BATSMAN.toString() +"'," + teamid +")";
                ResultSet resultSet = DatabaseService.insertData(query);
                cnt++;
            }
            for(int i = 0; i < 5; i++){
                String query = "INSERT INTO PLAYERDETAILS(name,rating,playertype,teamid) VALUES('player " + cnt +"',10,'"+ PlayerType.BOWLER.toString() +"',"+  teamid + ")";
//                System.out.println(query);
                ResultSet resultSet = DatabaseService.insertData(query);
                cnt++;
            }


        }
    }


}
