package com.cricketgame.utils;

import com.cricketgame.models.*;
import com.cricketgame.models.databasemodels.Player.BallDetails;
import com.cricketgame.models.enums.BallType;
import com.cricketgame.models.enums.Teams;

import java.util.ArrayList;
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
                if(over.getBalls().get(j).getStriker().getName().equals(player.getName())) score += over.getBalls().get(j).getRunsOnTheBall();
            }
        }
        return score;
    }

    public static int getWicketsTakenByPlayer(Inning inning, Player player) {
        int wicketsTaken = 0;
        for (int i = 0; i < inning.getOvers().size(); i++) {
            Over over = inning.getOvers().get(i);
            if(over.getBowler().getName().equals(player.getName())) {
                for (int j = 0; j < over.getBalls().size(); j++) {
                    if (over.getBalls().get(j).getBallType() == BallType.WICKET) wicketsTaken += 1;
                }
            }
        }
        return wicketsTaken;
    }

    public static ArrayList<BallDetails> getBallsPlayed(Player player, Inning inning){
        ArrayList <BallDetails> ballsPlayed = new ArrayList<BallDetails>();
        for (int i = 0; i < inning.getOvers().size(); i++) {
            Over over = inning.getOvers().get(i);
            for (int j = 0; j < over.getBalls().size(); j++) {
                if(over.getBalls().get(j).getStriker().getName().equals(player.getName())) {
                    Ball ball = over.getBalls().get(j);
                    BallDetails ballDetails = new BallDetails();
                    ballDetails.setScoreInTheBall(ball.getRunsOnTheBall());
                    ballDetails.setBallType(ball.getBallType());
                    ballDetails.setOver(i+1);
                    ballDetails.setBallNumber(j+1);
                    ballsPlayed.add(ballDetails);
                }
            }
        }
        return ballsPlayed;
    }

    public static ArrayList<Player> getPlayersWhoseWicketsIsTaken(Inning inning, Player player) {
        ArrayList <Player> playersWhomWicketIsTaken = new ArrayList<Player>();
        for (int i = 0; i < inning.getOvers().size(); i++) {
            Over over = inning.getOvers().get(i);
            if(over.getBowler().getName().equals(player.getName())) {
                for (int j = 0; j < over.getBalls().size(); j++) {
                    if (over.getBalls().get(j).getBallType() == BallType.WICKET){
                        playersWhomWicketIsTaken.add(over.getBalls().get(j).getStriker());
                    }
                }
            }
        }
        return playersWhomWicketIsTaken;
    }

}

