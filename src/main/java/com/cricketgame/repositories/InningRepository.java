package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseServiceImpl;
import com.cricketgame.models.Ball;
import com.cricketgame.models.Inning;
import com.cricketgame.models.Over;
import com.cricketgame.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class InningRepository {

    private static int inningId;
    private static int battingTeamId;
    private static int bowlingTeamId;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    DatabaseServiceImpl databaseService;

    public ResultSet createInning(Team team1, Team team2) throws SQLException {
        battingTeamId = teamRepository.getTeamId(String.valueOf(team1.getName()));
        bowlingTeamId = teamRepository.getTeamId(String.valueOf(team2.getName()));
        String query = "INSERT INTO INNINGDETAILS(battingteam,bowlingteam,matchid) values(" + battingTeamId + "," + bowlingTeamId + "," + matchRepository.matchId +")" ;
        ResultSet result = databaseService.insertData(query);
        result.next();
        inningId = result.getInt(1);
        return result;
    }

    public void insertInningData(@NonNull Inning inning) throws SQLException {

        ArrayList<Over> overs = inning.getOvers();
        for(Over over : overs){
            int bowlerId = playerRepository.getPlayerId(bowlingTeamId,over.getBowler().getName());
            String query = "INSERT INTO OVERDETAILS(playerid,inningid) VALUES(" + bowlerId + "," + inningId + ")";
            ResultSet result = databaseService.insertData(query);
            result.next();
            int overId = result.getInt(1);
            insertBallDetails(overId,over);
        }

    }

    public void insertBallDetails(int overId , Over over) throws SQLException {

        ArrayList <Ball> balls = over.getBalls();
        for(Ball ball : balls){
            int batsmanId = playerRepository.getPlayerId(battingTeamId,ball.getStriker().getName());
            int runsOnTheBall = ball.getRunsOnTheBall();
            String ballType = ball.getBallType().toString();
            String query = "INSERT INTO BALLDETAILS(playerid,runs,balltype,overid,inningid) values(" + batsmanId + "," + runsOnTheBall + ",'" + ballType +"'," + overId +"," + inningId +")";
            databaseService.insertData(query);
        }
    }

    public  ArrayList<Integer> getInnings(int matchId) throws SQLException {

        String query = "SELECT * FROM INNINGDETAILS WHERE MATCHID =" + matchId;
        ResultSet result = databaseService.getResult(query);
        ArrayList <Integer> inningsIds = new ArrayList<Integer>();
        while(result.next()){
            inningsIds.add(result.getInt(1));
        }
        return inningsIds;
    }
}
