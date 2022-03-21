package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseConnector;
import com.cricketgame.models.Ball;
import com.cricketgame.models.Over;
import com.cricketgame.models.Player;
import com.cricketgame.models.enums.BallType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class BallRepository {

    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    DatabaseConnector databaseConnector;

    /**
     * Generate all the balls for the current over
     *
     * @param overId
     * @return
     * @throws SQLException
     */
    public ArrayList<Ball> createBalls(int overId) throws SQLException {

        String query = "SELECT * FROM BALLDETAILS WHERE OVERID = " + overId;
        ResultSet resultSet = databaseConnector.getResult(query);
        ArrayList<Ball> balls = new ArrayList<Ball>();

        while (resultSet.next()) {
            int strikerId = resultSet.getInt(2);
            Player striker = playerRepository.createPlayer(strikerId);
            Ball ball = new Ball();
            ball.setRunsOnTheBall(resultSet.getInt(3));
            ball.setBallType(BallType.valueOf(resultSet.getString(4)));
            ball.setStriker(striker);
            balls.add(ball);
        }

        return balls;
    }

    /**
     * Insert ball details data in the table
     *
     * @param overId
     * @param battingTeamId
     * @param inningId
     * @param over
     * @throws SQLException
     */
    public void insertBallDetails(int overId, int battingTeamId, int inningId, Over over) throws SQLException {

        ArrayList<Ball> balls = over.getBalls();
        for (Ball ball : balls) {
            int batsmanId = playerRepository.getPlayerId(battingTeamId, ball.getStriker().getName());
            int runsOnTheBall = ball.getRunsOnTheBall();
            String ballType = ball.getBallType().toString();
            String query = "INSERT INTO BALLDETAILS(playerid,runs,balltype,overid,inningid) values(" + batsmanId + "," + runsOnTheBall + ",'" + ballType + "'," + overId + "," + inningId + ")";
            databaseConnector.insertData(query);
        }
    }
}
