package com.cricketgame.service;

import com.cricketgame.Constants;
import com.cricketgame.models.Inning;
import com.cricketgame.models.Player;
import com.cricketgame.models.PlayerType;
import com.cricketgame.models.Team;
import com.cricketgame.utils.InningUtils;
import com.cricketgame.utils.MatchUtils;

public class InningService {

    int strikerIndex;
    int nonStrikerIndex;
    Inning inning;
    boolean firstInningDone;
    int oppositeTeamScore;

    InningService(int overs, Team battingTeam, Team bowlingTeam, boolean firstInningDone, int oppositeTeamScore) {
        this.inning = new Inning(battingTeam, bowlingTeam, overs);
        strikerIndex = 0;
        nonStrikerIndex = 1;
        this.firstInningDone = firstInningDone;
        this.oppositeTeamScore = oppositeTeamScore;
    }

    public void startInning() {

        System.out.println("\n ==> " + inning.battingTeam.name + " is Batting");

        for (int i = 1; i <= inning.overs; i++) {
            int currentBowlerIndex = MatchUtils.getRandomNumber(0, 10);
            if(checkScoreMoreThenOppositeTeam() == true) return ;
            System.out.println("\n" + inning.bowlingTeam.players.get(currentBowlerIndex).name + " is Bowling and the over is = " + i + "\n");
            startOver(currentBowlerIndex);
        }

    }

    public void startOver(int currentBowlerIndex) {

        for (int j = 1; j <= Constants.TOTAL_BALLS_IN_ONE_OVER; j++) {

            if(checkScoreMoreThenOppositeTeam() == true) return ;

            int scoreInTheBall = MatchUtils.getRandomNumber(0, Constants.PLAYER_FACTOR);

            if (scoreInTheBall == 5 || scoreInTheBall == 0) continue; // counted as 0 runs

            if (scoreInTheBall > Constants.MAX_RUNS_IN_ONE_BALL) {
                inning.wicketsTaken.set(currentBowlerIndex, inning.wicketsTaken.get(currentBowlerIndex) + 1);
                handleWicket();
                showScoreBoard();
                continue;
            }

            inning.scoreOfPlayers.set(strikerIndex, inning.scoreOfPlayers.get(strikerIndex) + scoreInTheBall);
            handleRuns(scoreInTheBall);
            inning.score += scoreInTheBall;

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            showScoreBoard();
        }
    }

    boolean checkScoreMoreThenOppositeTeam(){
        if(firstInningDone == true && oppositeTeamScore < inning.score){
            return true;
        }
        else{
            return false;
        }
    }

    // handle wickets when the striker got out by currentBowler
    public void handleWicket() {

        inning.wickets += 1;
        inning.isOut.set(strikerIndex,true);
        System.out.println((inning.battingTeam.players.get(strikerIndex).name) + " is out with score of " + (inning.scoreOfPlayers.get(strikerIndex)));

        if (inning.wickets == Constants.TOTAL_WICKETS) {
            strikerIndex = nonStrikerIndex;
            return;
        }
        strikerIndex = InningUtils.getNextBatsman(strikerIndex, nonStrikerIndex, inning.isOut);
    }

    // handle runs in striker hits the ball
    public void handleRuns(int scoreInTheBall) {

        if (scoreInTheBall % 2 == 1) {
            swapPlayer(strikerIndex, nonStrikerIndex);
        }

        if (scoreInTheBall % 2 == 0) {
            System.out.println((inning.battingTeam.players.get(strikerIndex).name) + " hits an " + scoreInTheBall);
        }
    }

    // swap striker and non-striker for odd runs
    public void swapPlayer(int strikerIndex, int nonStrikerIndex) {
        int temp = strikerIndex;
        strikerIndex = nonStrikerIndex;
        nonStrikerIndex = temp;
    }

    void showScoreBoard() {
        System.out.println("|" + inning.battingTeam.name + " " + inning.score + "/" + inning.wickets + "|");
    }


    int updatePlayerFactor(int strikerIndex, int currentBowlerIndex) {
        // will update on the basis of skills of batsman and bowler
        Player striker = inning.battingTeam.players.get(strikerIndex);
        Player bowler = inning.bowlingTeam.players.get(currentBowlerIndex);
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
