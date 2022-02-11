package com.cricketgame;

public class MatchService {

    Player nonStriker;
    Player currentBatsman;
    int playerFactor = 9;

    void play(int overs, Team battingTeam , Team bowlingTeam){

        currentBatsman = battingTeam.players[0];
        nonStriker = battingTeam.players[1];

        System.out.println("\n ==> " + battingTeam.name + " is Batting");

        for (int i = 0; i < overs; i++) {

            Player currentBowler = bowlingTeam.players[(int) (Math.random() * 1000) % 11];
            System.out.println("\n" + currentBowler.name + " is Bowling\n");

            for (int j = 0; j <= 6; j++) {

                int scored = (int) (Math.random() * 1000) % playerFactor;

                if (scored == 5) continue;

                if (scored >= 7) {
                    battingTeam.wickets += 1;
                    System.out.println((currentBatsman.name) + " is out with score of " + (currentBatsman.score));
                    //                System.out.print("\r");
                    if (battingTeam.wickets == 10) {
                        currentBatsman = nonStriker;
                        return;
                    }
                    currentBatsman = battingTeam.players[battingTeam.wickets + 1];
                    currentBowler.wicket += 1;
                    continue;
                }

                currentBatsman.score += scored;

                if (scored == 1 || scored == 3) {
                    Player temp = currentBatsman;
                    currentBatsman = nonStriker;
                    nonStriker = temp;
                    playerFactor = updatePlayerFactor(currentBatsman, currentBowler);
                }

                if (scored == 6 || scored == 4) {
                    System.out.println(currentBatsman.name + " scored " + scored);
//                System.out.print("\r");
                }

                battingTeam.score += scored;
                try {
                    Thread.sleep(1000);
//                System.out.println(score);
                }
                catch (Exception e){
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

            System.out.println(p.name + "  \t" + p.wicket + "\t\t  " + p.score);

        }
    }

    void showScoreBoard(Team team){
        System.out.println("|" + team.name + " " + team.score + "/" + team.wickets+"|");
    }

    int updatePlayerFactor(Player batsman, Player bowler) {
        // will update on the basis of skills of batsman and bowler
        return 9;
    }
}
