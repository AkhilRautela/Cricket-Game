package com.cricketgame.utils;

import com.cricketgame.models.*;

import java.util.Random;

public class MatchUtils {

    public static boolean checkValidTeamName(String name) {
        try {
            Teams.valueOf(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getRandomNumber(int low, int high) {
        Random random = new Random();
        return random.ints(low, high).findFirst().getAsInt();
    }

    public static int getTotalWickets(Inning inning) {
        int wickets = 0;
        for (int i = 0; i < inning.getOvers().size(); i++) {
            Over over = inning.getOvers().get(i);
            for (int j = 0; j < over.getBalls().size(); j++) {
                if(over.getBalls().get(j).getBallType() == BallType.WICKET) wickets += 1;
            }
        }
        return wickets;
    }

    public static int getScore(Inning inning) {
        int score = 0;
        for (int i = 0; i < inning.getOvers().size(); i++) {
            Over over = inning.getOvers().get(i);
            for (int j = 0; j < over.getBalls().size(); j++) {
                score += over.getBalls().get(j).getRunsOnTheBall();
            }
        }
        return score;
    }

    public static int getScoreOfPlayer(Inning inning, Player player) {
        int score = 0;
        for (int i = 0; i < inning.getOvers().size(); i++) {
            Over over = inning.getOvers().get(i);
            for (int j = 0; j < over.getBalls().size(); j++) {
                if(over.getBalls().get(j).getStriker() == player) score += over.getBalls().get(j).getRunsOnTheBall();
            }
        }
        return score;
    }

    public static int getWicketsTakenByPlayer(Inning inning, Player player) {
        int wicketsTaken = 0;
        for (int i = 0; i < inning.getOvers().size(); i++) {
            Over over = inning.getOvers().get(i);
            if(over.getBowler() == player) {
                for (int j = 0; j < over.getBalls().size(); j++) {
                    if (over.getBalls().get(j).getBallType() == BallType.WICKET) wicketsTaken += 1;
                }
            }
        }
        return wicketsTaken;
    }
}

