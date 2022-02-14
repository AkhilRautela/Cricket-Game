package com.cricketgame;

public class MatchService {

    int playerFactor = 9;

    void play(int overs, Team battingTeam, Team bowlingTeam) {

        battingTeam.batsman = battingTeam.players[0];
        battingTeam.nonStriker = battingTeam.players[1];

        System.out.println("\n ==> " + battingTeam.name + " is Batting");

        for (int i = 0; i < overs; i++) {

            Player currentBowler = bowlingTeam.players[(int) (Math.random() * 1000) % 11];
            System.out.println("\n" + currentBowler.name + " is Bowling\n");

            for (int j = 0; j <= 6; j++) {

                int scored = (int) (Math.random() * 1000) % playerFactor;

                if (scored == 5) continue; // counted as 0 runs

                if (scored >= 7) {
                    battingTeam.wickets += 1;
                    System.out.println((battingTeam.batsman.name) + " is out with score of " + (battingTeam.batsman.score));
                    //                System.out.print("\r");
                    if (battingTeam.wickets == 10) {
                        battingTeam.batsman = battingTeam.nonStriker;
                        return;
                    }
                    battingTeam.batsman = battingTeam.players[battingTeam.wickets + 1];
                    currentBowler.wicket += 1;
                    continue;
                }

                battingTeam.batsman.score += scored;

                if (scored == 1 || scored == 3) {
                    Player temp = battingTeam.batsman;
                    battingTeam.batsman = battingTeam.nonStriker;
                    battingTeam.nonStriker = temp;
                    playerFactor = updatePlayerFactor(battingTeam.batsman, currentBowler);
                }

                if (scored == 6 || scored == 4) {
                    System.out.println(battingTeam.batsman.name + " scored " + scored);
//                System.out.print("\r");
                }

                battingTeam.score += scored;
                try {
                    Thread.sleep(1000);
//                System.out.println(score);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showScoreBoard(battingTeam);
            }
        }

    }

    void getStats(Team team) {
        System.out.println("\nStats for " + team.name);
        System.out.println("Name \t Wickets \t Runs");
        for (Player p : team.players) {
            if (p == team.batsman || p == team.nonStriker) {
                System.out.println("* " + p.name + "\t" + p.wicket + "\t\t" + p.score);
            } else {
                System.out.println(p.name + "  \t" + p.wicket + "\t\t" + p.score);
            }
        }
    }

    void showScoreBoard(Team team) {
        System.out.println("|" + team.name + " " + team.score + "/" + team.wickets + "|");
    }

    int updatePlayerFactor(Player batsman, Player bowler) {
        // will update on the basis of skills of batsman and bowler
        if(bowler.bowlingRating >= 8 && batsman.battingRating >= 8) {
            return 9;
        }
        else if(bowler.bowlingRating >= 8){
            return 10;
        }
        else if(batsman.battingRating >= 8){
            return 8;
        }
        return 9;
    }
}
