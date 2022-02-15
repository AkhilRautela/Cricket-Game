package com.cricketgame.service;

import com.cricketgame.models.Player;
import com.cricketgame.models.Team;
import com.cricketgame.utils.MatchUtils;

public class InningService {

    int playerFactor = 9;

    public void startInning(int overs, Team battingTeam, Team bowlingTeam) {

        battingTeam.batsman = battingTeam.players.get(0);
        battingTeam.nonStriker = battingTeam.players.get(1);

        System.out.println("\n ==> " + battingTeam.name + " is Batting");

        for (int i = 1; i <= overs; i++) {

            Player currentBowler = bowlingTeam.players.get(MatchUtils.getRandomNumber(0,10));
            System.out.println("\n" + currentBowler.name + " is Bowling and the over is = " + i + "\n");

            for (int j = 0; j <= 6; j++) {

                int scoreInTheBall = MatchUtils.getRandomNumber(0,playerFactor);

                if (scoreInTheBall == 5) continue; // counted as 0 runs

                if (scoreInTheBall >= 7) {
                    battingTeam.wickets += 1;
                    System.out.println((battingTeam.batsman.name) + " is out with score of " + (battingTeam.batsman.score));
                    //                System.out.print("\r");
                    if (battingTeam.wickets == 10) {
                        battingTeam.batsman = battingTeam.nonStriker;
                        return;
                    }

                    battingTeam.batsman.isOut = true;

                    for (int k = 0; k < 11; k++)
                        if (battingTeam.players.get(k).isOut == false && battingTeam.nonStriker != battingTeam.players.get(k)) {
                            battingTeam.batsman = battingTeam.players.get(k);
                            break;
                        }
                    currentBowler.wicket += 1;
                    continue;
                }

                battingTeam.batsman.score += scoreInTheBall;

                if (scoreInTheBall == 1 || scoreInTheBall == 3) {
                    Player temp = battingTeam.batsman;
                    battingTeam.batsman = battingTeam.nonStriker;
                    battingTeam.nonStriker = temp;
                    playerFactor = updatePlayerFactor(battingTeam.batsman, currentBowler);
                }

                if (scoreInTheBall == 6 || scoreInTheBall == 4) {
                    System.out.println(battingTeam.batsman.name + " scored " + scoreInTheBall);
//                System.out.print("\r");
                }

                battingTeam.score += scoreInTheBall;

                if (bowlingTeam.havePlayed && battingTeam.score > bowlingTeam.score) {
                    return;
                }

                try {
                    Thread.sleep(1000);
//                System.out.println(score);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showScoreBoard(battingTeam);
            }
        }
        battingTeam.havePlayed = true;

    }

    void showScoreBoard(Team team) {
        System.out.println("|" + team.name + " " + team.score + "/" + team.wickets + "|");
    }

    int updatePlayerFactor(Player batsman, Player bowler) {
        // will update on the basis of skills of batsman and bowler
        if (bowler.bowlingRating >= 8 && batsman.battingRating >= 8) {
            return 9;
        } else if (bowler.bowlingRating >= 8) {
            return 10;
        } else if (batsman.battingRating >= 8) {
            return 8;
        }
        return 9;
    }
}
