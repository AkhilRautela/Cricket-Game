package com.cricketgame.service;

import com.cricketgame.constants.Constants;
import com.cricketgame.models.*;
import com.cricketgame.models.enums.BallType;
import com.cricketgame.models.enums.PlayerType;
import com.cricketgame.utils.InningUtils;
import com.cricketgame.utils.MatchUtils;
import org.springframework.stereotype.Component;

@Component
public class InningService {

    int strikerIndex;
    int nonStrikerIndex;
    int playerFactor;

    InningService() {
        this.playerFactor = Constants.PLAYER_FACTOR;
    }

    /**
     * Start the inning
     * @param inning
     */
    public void startInning(Inning inning) {
        this.strikerIndex = 0;
        this.nonStrikerIndex = 1;

        System.out.println("\n ==> " + inning.getBattingTeam().getName() + " is Batting");

        for (int i = 1; i <= inning.getTotalOvers(); i++) {
            int currentBowlerIndex = InningUtils.getBowlerForTheOver(inning.getBowlingTeam().getPlayers());
            if (InningUtils.checkScoreMoreThenOppositeTeam(inning) || InningUtils.checkAllOut(inning)) return;
            System.out.println("\n" + inning.getBowlingTeam().getPlayers().get(currentBowlerIndex).getName() + " is Bowling and the over is = " + i + "\n");
            Over over = new Over();
            inning.getOvers().add(over);
            startOver(inning,i - 1, currentBowlerIndex);
            showScoreBoard(inning);
        }

    }

    public void startOver(Inning inning, int currentOver, int currentBowlerIndex) {

        inning.getOvers().get(currentOver).setBowler(inning.getBowlingTeam().getPlayers().get(currentBowlerIndex));

        playerFactor = updatePlayerFactor(inning, currentBowlerIndex);

        for (int j = 1; j <= Constants.TOTAL_BALLS_IN_ONE_OVER; j++) {

            if (InningUtils.checkScoreMoreThenOppositeTeam(inning) || InningUtils.checkAllOut(inning)) return ;

            Ball ball = new Ball();
            ball.setStriker(inning.getBattingTeam().getPlayers().get(strikerIndex));

            int scoreInTheBall = MatchUtils.getRandomNumber(0, Constants.PLAYER_FACTOR);

            if (scoreInTheBall == 5 || scoreInTheBall == 0) { // counted as 0 runs
                ball.setBallType(BallType.NOTARUN);
                inning.getOvers().get(currentOver).getBalls().add(ball);
                continue;
            }
            // Condition for wicket
            if (scoreInTheBall > Constants.MAX_RUNS_IN_ONE_BALL) {
                Player striker = inning.getBattingTeam().getPlayers().get(strikerIndex);
                System.out.println(striker.getName() + " is out with score of " + (MatchUtils.getScoreOfPlayer(inning,striker)));
                ball.setBallType(BallType.WICKET);
                inning.getOvers().get(currentOver).getBalls().add(ball);
                strikerIndex = InningUtils.getNextBatsman(strikerIndex,nonStrikerIndex,inning);
                showScoreBoard(inning);
                continue;
            }

            ball.setBallType(BallType.RUN);
            ball.setRunsOnTheBall(scoreInTheBall);
            inning.getOvers().get(currentOver).getBalls().add(ball);
            handleRuns(scoreInTheBall, inning, currentBowlerIndex);
            showScoreBoard(inning);

        }

    }


    // handle runs in striker hits the ball
    public void handleRuns(int scoreInTheBall, Inning inning, int currentBowlerIndex) {

        String strikerName = inning.getBattingTeam().getPlayers().get(strikerIndex).getName();

        if (scoreInTheBall % 2 == 1) {
            int batsmanOnPitch[] = InningUtils.swapPlayer(strikerIndex, nonStrikerIndex);
            strikerIndex = batsmanOnPitch[0];
            nonStrikerIndex = batsmanOnPitch[1];
            playerFactor = updatePlayerFactor(inning, currentBowlerIndex);
        }

        if (scoreInTheBall % 2 == 0) {
            System.out.println(strikerName + " hits an " + scoreInTheBall);
        }
    }



    private void showScoreBoard(Inning inning) {
        System.out.println("|" + inning.getBattingTeam().getName() + " " + MatchUtils.getScore(inning) + "/" + MatchUtils.getTotalWickets(inning) + "|");
    }

    // will update on the basis of skills of batsman and bowler
    private int updatePlayerFactor(Inning inning, int currentBowlerIndex) {
        Player bowler = inning.getBowlingTeam().getPlayers().get(currentBowlerIndex);
        Player striker = inning.getBattingTeam().getPlayers().get(strikerIndex);

        if (striker.getPlayertype() == PlayerType.BATSMAN) {
            if (striker.getRating() >= 8 && bowler.getRating() >= 8) {
                return 9;
            }
            if (striker.getRating() >= 8) return 8;
            else return 10;
        }

        if(bowler.getRating() < 8) return 9;
        return 10;
    }
}