package com.cricketgame.repositories;

import com.cricketgame.database.DatabaseConnector;
import com.cricketgame.models.Ball;
import com.cricketgame.models.Inning;
import com.cricketgame.models.Over;
import com.cricketgame.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class OverRepository {

    @Autowired
    DatabaseConnector databaseConnector;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    BallRepository ballRepository;

    /**
     * create Overs object for the given inning id.
     *
     * @param inningId
     * @return
     * @throws SQLException
     */
    public ArrayList<Over> createOvers(int inningId) throws SQLException {
        String query = "SELECT * FROM OVERDETAILS WHERE INNINGID = " + inningId;
        ResultSet resultSet = databaseConnector.getResult(query);
        ArrayList<Over> overs = new ArrayList<Over>();

        while (resultSet.next()) {
            int overId = resultSet.getInt(1);
            int bowlerId = resultSet.getInt(2);
            Player bowler = playerRepository.createPlayer(bowlerId);
            Over over = new Over();
            ArrayList<Ball> balls = ballRepository.createBalls(overId);
            over.setBalls(balls);
            over.setBowler(bowler);
            overs.add(over);
        }

        return overs;
    }

    /**
     * Insert Over data to the overs table.
     *
     * @param inning
     * @param inningId
     * @param bowlingTeamId
     * @param battingTeamId
     * @throws SQLException
     */
    public void insertOverData(@NonNull Inning inning, int inningId, int bowlingTeamId, int battingTeamId) throws SQLException {
        ArrayList<Over> overs = inning.getOvers();
        for (Over over : overs) {
            int bowlerId = playerRepository.getPlayerId(bowlingTeamId, over.getBowler().getName());
            String query = "INSERT INTO OVERDETAILS(playerid,inningid) VALUES(" + bowlerId + "," + inningId + ")";
            ResultSet result = databaseConnector.insertData(query);
            result.next();
            int overId = result.getInt(1);
            ballRepository.insertBallDetails(overId, battingTeamId, inningId, over);
        }

    }
}
