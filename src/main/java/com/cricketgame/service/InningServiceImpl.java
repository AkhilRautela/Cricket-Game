package com.cricketgame.service;

import com.cricketgame.constants.Constants;
import com.cricketgame.models.*;
import com.cricketgame.models.enums.BallType;
import com.cricketgame.models.enums.PlayerType;
import com.cricketgame.utils.GeneralUtils;
import com.cricketgame.utils.InningUtils;
import org.springframework.stereotype.Service;

@Service
public class InningServiceImpl {

    /**
     * Start the inning
     *
     * @param inning
     */
    public void startInning(Inning inning) {

        Strike strike = new Strike();
        strike.setStrikerIndex(0);
        strike.setNonStrikerIndex(1);


        for (int i = 1; i <= inning.getTotalOvers(); i++) {
            strike.setCurrentBowlerIndex(InningUtils.getBowlerIndexForTheOver(inning.getBowlingTeam().getPlayers()));
            if (InningUtils.isScoreMoreThenOppositeTeam(inning) || InningUtils.isAllOut(inning)) return;
            Over over = new Over();
            inning.getOvers().add(over);
            startOver(inning, i - 1, strike);
        }

    }

    /**
     * Start the over correspondin to the inning provided
     *
     * @param inning
     * @param currentOver
     * @param strike
     */
    public void startOver(Inning inning, int currentOver, Strike strike) {

        inning.getOvers().get(currentOver).setBowler(inning.getBowlingTeam().getPlayers().get(strike.getCurrentBowlerIndex()));

        strike.setPlayerFactor(updatePlayerFactor(inning, strike));

        for (int j = 1; j <= Constants.TOTAL_BALLS_IN_ONE_OVER; j++) {

            if (InningUtils.isScoreMoreThenOppositeTeam(inning) || InningUtils.isAllOut(inning)) return;

            Ball ball = new Ball();
            ball.setStriker(inning.getBattingTeam().getPlayers().get(strike.getStrikerIndex()));

            int scoreInTheBall = GeneralUtils.getRandomNumber(0, strike.getPlayerFactor());

            if (scoreInTheBall == 5 || scoreInTheBall == 0) { // counted as 0 runs
                ball.setBallType(BallType.NOTARUN);
                inning.getOvers().get(currentOver).getBalls().add(ball);
                continue;
            }

            if (scoreInTheBall > Constants.MAX_RUNS_IN_ONE_BALL) {
                Player striker = inning.getBattingTeam().getPlayers().get(strike.getStrikerIndex());
                ball.setBallType(BallType.WICKET);
                inning.getOvers().get(currentOver).getBalls().add(ball);
                strike.setStrikerIndex(InningUtils.getNextBatsman(strike.getStrikerIndex(), strike.getNonStrikerIndex(), inning));
                continue;
            }

            ball.setBallType(BallType.RUN);
            ball.setRunsOnTheBall(scoreInTheBall);
            inning.getOvers().get(currentOver).getBalls().add(ball);
            handleRuns(scoreInTheBall, inning, strike);
        }

    }

    /**
     * Handle the run scored in an ball
     *
     * @param scoreInTheBall
     * @param inning
     * @param strike
     */
    public void handleRuns(int scoreInTheBall, Inning inning, Strike strike) {

        String strikerName = inning.getBattingTeam().getPlayers().get(strike.getStrikerIndex()).getName();

        if (scoreInTheBall % 2 == 1) {
            int temp = strike.getStrikerIndex();
            strike.setStrikerIndex(strike.getNonStrikerIndex());
            strike.setNonStrikerIndex(temp);
            strike.setPlayerFactor(updatePlayerFactor(inning, strike));
        }

    }

    /**
     * Update player factor for the new batsman and bowler
     *
     * @param inning
     * @param strike
     * @return
     */
    private int updatePlayerFactor(Inning inning, Strike strike) {
        Player bowler = inning.getBowlingTeam().getPlayers().get(strike.getCurrentBowlerIndex());
        Player striker = inning.getBattingTeam().getPlayers().get(strike.getStrikerIndex());

        if (striker.getPlayertype() == PlayerType.BATSMAN) {
            if (striker.getRating() >= 8 && bowler.getRating() >= 8) {
                return 9;
            }
            if (striker.getRating() >= 8) return 8;
            else return 10;
        }

        if (bowler.getRating() < 8) return 9;
        return 10;
    }
}
