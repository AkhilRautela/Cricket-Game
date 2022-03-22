package com.cricketgame.utils;

import com.cricketgame.constants.Constants;
import com.cricketgame.models.Ball;
import com.cricketgame.models.Inning;
import com.cricketgame.models.Over;
import com.cricketgame.models.Player;
import com.cricketgame.models.databasemodels.Player.BallDetails;
import com.cricketgame.models.enums.BallType;
import com.cricketgame.models.enums.PlayerType;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class InningUtils {
    /**
     * get next batsman other then player that is out or not a nonStriker
     *
     * @param strikerIndex
     * @param nonStrikerIndex
     * @param inning
     * @return
     */
    public static int getNextBatsman(int strikerIndex, int nonStrikerIndex, Inning inning) {
        for (int i = 0; i < 11; i++) {
            if (getWicketTaker(inning, inning.getBattingTeam().getPlayers().get(i)) == null && i != nonStrikerIndex) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Check wether a player is out or not in an inning.
     *
     * @param inning
     * @param player
     * @return
     */
    public static Player getWicketTaker(Inning inning, Player player) {
        for (int i = 0; i < inning.getOvers().size(); i++) {
            Over over = inning.getOvers().get(i);
            for (int j = 0; j < over.getBalls().size(); j++) {
                Ball b = over.getBalls().get(j);
                if (b.getBallType() == BallType.WICKET && b.getStriker().getName().equals(player.getName())) {
                    return over.getBowler();
                }
            }
        }
        return null;
    }

    /**
     * Select next bowler for the over.
     *
     * @param players
     * @return
     */
    public static int getBowlerIndexForTheOver(ArrayList<Player> players) {
        ArrayList<Integer> bowlers = new ArrayList<Integer>();
        for (int i = 0; i < 11; i++) {
            if (players.get(i).getPlayertype() == PlayerType.BOWLER) {
                bowlers.add(i);
            }
        }
        return bowlers.get(GeneralUtils.getRandomNumber(0, bowlers.size() - 1));
    }


    /**
     * Check wether the opposite team has played , if played then score greater than that team or not.
     *
     * @param inning
     * @return
     */
    public static boolean isScoreMoreThenOppositeTeam(Inning inning) {
        return inning.isFirstInningDone() && inning.getOppositeTeamScore() < getScore(inning);
    }


    /**
     * Check wether all players in the inning are out or not.
     *
     * @param inning
     * @return
     */
    public static boolean isAllOut(Inning inning) {
        return getTotalWickets(inning) == Constants.TOTAL_WICKETS;
    }

    /**
     * get total wicket taken in an innings
     *
     * @param inning
     * @return
     */
    public static int getTotalWickets(Inning inning) {
        AtomicInteger totalWickets = new AtomicInteger();
        
        inning.getOvers().stream().forEach(over -> {
            totalWickets.addAndGet((int) over.getBalls().stream().filter(ball -> ball.getBallType() == BallType.WICKET).count());
        });
        return totalWickets.get();
    }

    /**
     * Get Score of the Batting team in the inning
     *
     * @param inning
     * @return
     */
    public static int getScore(Inning inning) {
        AtomicInteger score = new AtomicInteger();
        inning.getOvers().stream().forEach(over -> {
            over.getBalls().stream().forEach(ball -> {score.addAndGet(ball.getRunsOnTheBall());});
        });
        return score.get();
    }

    /**
     * Get score of a particular player in the inning
     *
     * @param inning
     * @param player
     * @return
     */
    public static int getScoreOfPlayer(Inning inning, Player player) {
        AtomicInteger score = new AtomicInteger();
        inning.getOvers().stream().forEach(over -> {
            over.getBalls().stream().filter(ball -> ball.getStriker().getName().equals(player.getName())).forEach(
                    ball -> {
                        score.addAndGet(ball.getRunsOnTheBall());
                    }
            );
        });
        return score.get();
    }

    /**
     * get wicket taken by an player in the inning
     *
     * @param inning
     * @param player
     * @return
     */
    public static int getWicketsTakenByPlayer(Inning inning, Player player) {
        AtomicInteger wicketsTaken = new AtomicInteger();

        inning.getOvers().stream().filter(over -> over.getBowler().getName().equals(player.getName())).forEach(over -> {
            wicketsTaken.addAndGet((int) over.getBalls().stream().filter(ball -> ball.getBallType() == BallType.WICKET).count());
        });
        return wicketsTaken.get();
    }

    /**
     * Get all the balls played by the player
     *
     * @param player
     * @param inning
     * @return
     */
    public static ArrayList<BallDetails> getAllBallsPlayed(Player player, Inning inning) {
        ArrayList<BallDetails> ballsPlayed = new ArrayList<BallDetails>();
        for (int i = 0; i < inning.getOvers().size(); i++) {
            Over over = inning.getOvers().get(i);
            for (int j = 0; j < over.getBalls().size(); j++) {
                if (over.getBalls().get(j).getStriker().getName().equals(player.getName())) {
                    Ball ball = over.getBalls().get(j);
                    BallDetails ballDetails = new BallDetails();
                    ballDetails.setScoreInTheBall(ball.getRunsOnTheBall());
                    ballDetails.setBallType(ball.getBallType());
                    ballDetails.setOver(i + 1);
                    ballDetails.setBallNumber(j + 1);
                    ballsPlayed.add(ballDetails);
                }
            }
        }
        return ballsPlayed;
    }

    /**
     * Fetch players whose wicket is taken by the player
     *
     * @param inning
     * @param player
     * @return
     */
    public static ArrayList<Player> getAllPlayersWhoseWicketsIsTaken(Inning inning, Player player) {
        ArrayList<Player> playersWhomWicketIsTaken = new ArrayList<Player>();
        inning.getOvers().stream().filter(over -> over.getBowler().getName().equals(player.getName())).forEach(over -> {
            over.getBalls().stream().filter(ball -> ball.getBallType() == BallType.WICKET).forEach(ball -> {playersWhomWicketIsTaken.add(ball.getStriker());});
        });
        return playersWhomWicketIsTaken;
    }
}
