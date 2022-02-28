package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseService;
import com.cricketgame.database.DatabaseUtils;
import com.cricketgame.models.Team;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InningRepository {
    public static int inningId;

    public static void createInning(Team team1, Team team2) throws SQLException {
        int battingteam = DatabaseUtils.getTeamId(String.valueOf(team1.getName()));
        int bowlingteam = DatabaseUtils.getTeamId(String.valueOf(team2.getName()));
        String query = "INSERT INTO INNINGDETAILS(battingteam,bowlingteam,matchid) values(" + battingteam + "," + bowlingteam + "," + MatchRepository.matchId +")" ;
        System.out.println(query);
        ResultSet result = DatabaseService.insertData(query);
        result.next();
        inningId = result.getInt(1);
    }

}
