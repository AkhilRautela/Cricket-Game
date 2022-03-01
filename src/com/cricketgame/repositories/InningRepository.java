package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseService;
import com.cricketgame.database.DatabaseUtils;
import com.cricketgame.models.Ball;
import com.cricketgame.models.Inning;
import com.cricketgame.models.Over;
import com.cricketgame.models.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InningRepository {

    private static int inningId;
    private static int battingTeamId;
    private static int bowlingTeamId;

    public static void createInning(Team team1, Team team2) throws SQLException {
        battingTeamId = DatabaseUtils.getTeamId(String.valueOf(team1.getName()));
        bowlingTeamId = DatabaseUtils.getTeamId(String.valueOf(team2.getName()));
        String query = "INSERT INTO INNINGDETAILS(battingteam,bowlingteam,matchid) values(" + battingTeamId + "," + bowlingTeamId + "," + MatchRepository.matchId +")" ;
        ResultSet result = DatabaseService.insertData(query);
        result.next();
        inningId = result.getInt(1);
    }

    public static void insertInningData(Inning inning) throws SQLException {

        ArrayList<Over> overs = inning.getOvers();
        for(Over over : overs){
            int bowlerId = DatabaseUtils.getPlayerId(bowlingTeamId,over.getBowler().getName());
            String query = "INSERT INTO OVERDETAILS(playerid,inningid) VALUES(" + bowlerId + "," + inningId + ")";
            ResultSet result = DatabaseService.insertData(query);
            result.next();
            int overId = result.getInt(1);
            insertBallDetails(overId,over);
        }

    }

    private static void insertBallDetails(int overId , Over over) throws SQLException {

        ArrayList <Ball> balls = over.getBalls();
        for(Ball ball : balls){
            int batsmanId = DatabaseUtils.getPlayerId(battingTeamId,ball.getStriker().getName());
            int runsOnTheBall = ball.getRunsOnTheBall();
            String ballType = ball.getBallType().toString();
            String query = "INSERT INTO BALLDETAILS(playerid,runs,balltype,overid,inningid) values(" + batsmanId + "," + runsOnTheBall + ",'" + ballType +"'," + overId +"," + inningId +")";
            DatabaseService.insertData(query);
        }
    }

}
