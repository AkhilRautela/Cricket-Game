package com.cricketgame.service;

import com.cricketgame.Constants;
import com.cricketgame.database.DatabaseService;
import com.cricketgame.database.DatabaseUtils;
import com.cricketgame.models.*;
import com.cricketgame.models.enums.BallType;
import com.cricketgame.repositories.InningRepository;
import com.cricketgame.repositories.MatchRepository;

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DataFetchService {

    Inning inning1;
    Inning inning2;

    public void getMatch(int matchId) throws SQLException {
        int firstInningId,secondInningId;
        ArrayList<Integer> inningsId = InningRepository.getInnings(matchId);
        inning1 = createInnings(inningsId.get(0));
        inning2 = createInnings(inningsId.get(1));
    }

    public Inning createInnings(int inningId) throws SQLException {
        String query = "SELECT * FROM INNINGDETAILS WHERE INNINGID = " + inningId;
        ResultSet resultSet = DatabaseService.getResult(query);
        resultSet.next();
        Team battingTeam = DatabaseUtils.createTeam(resultSet.getInt(2));
        Team bowlingTeam = DatabaseUtils.createTeam(resultSet.getInt(3));
        Inning inning = new Inning(battingTeam,bowlingTeam, Constants.ANY_CONSTANT,true,Constants.ANY_CONSTANT);
        ArrayList<Over> overs = createOvers(inningId);
        inning.setOvers(overs);
        return inning;
    }

    private ArrayList<Over> createOvers(int inningId) throws SQLException {
        String query = "SELECT * FROM OVERDETAILS WHERE INNINGID = " + inningId;
        ResultSet resultSet = DatabaseService.getResult(query);
        ArrayList <Over> overs = new ArrayList<Over>();

        while(resultSet.next()){
            int overId = resultSet.getInt(1);
            int bowlerId = resultSet.getInt(2);
            Player bowler = DatabaseUtils.createPlayer(bowlerId);
            Over over = new Over();
            ArrayList <Ball> balls = createBalls(overId);
            over.setBalls(balls);
            over.setBowler(bowler);
            overs.add(over);
        }

        return overs;
    }

    private ArrayList<Ball> createBalls(int overId) throws SQLException {

        String query = "SELECT * FROM BALLDETAILS WHERE OVERID = " + overId;
        ResultSet resultSet = DatabaseService.getResult(query);
        ArrayList <Ball> balls = new ArrayList<Ball>();

        while(resultSet.next()){
            int strikerId = resultSet.getInt(2);
            Player striker = DatabaseUtils.createPlayer(strikerId);
            Ball ball = new Ball();
            ball.setRunsOnTheBall(resultSet.getInt(3));
            ball.setBallType(BallType.valueOf(resultSet.getString(4)));
            ball.setStriker(striker);
            balls.add(ball);
        }


        return balls;
    }

    public void getResults() {
        MatchService matchService = new MatchService();
        matchService.setInning1(inning1);
        matchService.setInning2(inning2);
        matchService.showScoreBoard();
        matchService.getResults();
    }
}
