package com.cricketgame.service;

import com.cricketgame.Constants;
import com.cricketgame.models.*;
import com.cricketgame.utils.InningUtils;
import com.cricketgame.utils.MatchUtils;

public class InningService {

    int strikerIndex;
    int nonStrikerIndex;
    int currentBowlerIndex;
    int playerFactor;

    InningService() {
        this.strikerIndex = 0;
        this.nonStrikerIndex = 1;
        this.playerFactor = Constants.PLAYER_FACTOR;
    }

    public void startInning(Inning inning) {

        System.out.println("\n ==> " + inning.battingTeam.name + " is Batting");

        for (int i = 1; i <= inning.overs; i++) {
            currentBowlerIndex = MatchUtils.getRandomNumber(0, 10);
            if (checkScoreMoreThenOppositeTeam(inning) || checkAllOut(inning)) return;
            System.out.println("\n" + inning.bowlingTeam.players.get(currentBowlerIndex).name + " is Bowling and the over is = " + i + "\n");
            inning.Overs.add(startOver(inning));
        }

    }

    public Over startOver(Inning inning) {

        Over over = new Over();
        over.bowler = inning.bowlingTeam.players.get(currentBowlerIndex);

        for (int j = 1; j <= Constants.TOTAL_BALLS_IN_ONE_OVER; j++) {

            if (checkScoreMoreThenOppositeTeam(inning) || checkAllOut(inning)) return over;

            Ball ball = new Ball();
            ball.striker = inning.battingTeam.players.get(strikerIndex);

            int scoreInTheBall = MatchUtils.getRandomNumber(0, Constants.PLAYER_FACTOR);

            if (scoreInTheBall == 5 || scoreInTheBall == 0) { // counted as 0 runs
                ball.runsOnTheBall = 0;
                ball.ballType = BallType.NOTARUN;
                over.balls.add(ball);
                continue;
            }

            if (scoreInTheBall > Constants.MAX_RUNS_IN_ONE_BALL) {
                inning.wicketsTaken.set(currentBowlerIndex, inning.wicketsTaken.get(currentBowlerIndex) + 1);
                handleWicket(inning);
                ball.ballType = BallType.WICKET;
                over.balls.add(ball);
                showScoreBoard(inning);
                continue;
            }

            inning.scoreOfPlayers.set(strikerIndex, inning.scoreOfPlayers.get(strikerIndex) + scoreInTheBall);
            ball.ballType = BallType.RUN;
            handleRuns(scoreInTheBall, inning);
            inning.score += scoreInTheBall;

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            showScoreBoard(inning);
        }
        return over;
    }

    // check wether the opposite team has played , if played then score greater than that team
    private boolean checkScoreMoreThenOppositeTeam(Inning inning) {
        return inning.firstInningDone == true && inning.oppositeTeamScore < inning.score;
    }

    // handle wickets when the striker got out by currentBowler
    public void handleWicket(Inning inning) {

        inning.wickets += 1;
        inning.isOut.set(strikerIndex, true);
        System.out.println((inning.battingTeam.players.get(strikerIndex).name) + " is out with score of " + (inning.scoreOfPlayers.get(strikerIndex)));

        strikerIndex = InningUtils.getNextBatsman(strikerIndex, nonStrikerIndex, inning.isOut);
    }

    // handle runs in striker hits the ball
    public void handleRuns(int scoreInTheBall, Inning inning) {

        String strikerName = inning.battingTeam.players.get(strikerIndex).name;

        if (scoreInTheBall % 2 == 1) {
            swapPlayer(strikerIndex, nonStrikerIndex);
            Player bowler = inning.bowlingTeam.players.get(currentBowlerIndex);
            Player striker = inning.battingTeam.players.get(strikerIndex);
        }

        if (scoreInTheBall % 2 == 0) {
            System.out.println(strikerName + " hits an " + scoreInTheBall);
        }
    }

    private boolean checkAllOut(Inning inning) {
        if (inning.wickets == Constants.TOTAL_WICKETS) {
            strikerIndex = nonStrikerIndex;
            return true;
        }
        return false;
    }

    // swap striker and non-striker for odd runs
    private void swapPlayer(int strikerIndex, int nonStrikerIndex) {
        int temp = strikerIndex;
        strikerIndex = nonStrikerIndex;
        nonStrikerIndex = temp;
    }

    private void showScoreBoard(Inning inning) {
        System.out.println("|" + inning.battingTeam.name + " " + inning.score + "/" + inning.wickets + "|");
    }


    private int updatePlayerFactor(Player striker, Player bowler) {
        // will update on the basis of skills of batsman and bowler
        if (striker.playertype == PlayerType.BATSMAN && bowler.playertype == PlayerType.BALLER) {
            if (striker.rating >= 8 && bowler.rating >= 8) {
                return 8;
            }
            if (striker.rating >= 8) return 8;
            else return 9;
        }
        if (bowler.playertype == PlayerType.BALLER) {
            if (bowler.rating >= 8) {
                return 10;
            }
        } else {
            if (striker.rating >= 8) {
                return 8;
            }
        }
        return 9;
    }
}
