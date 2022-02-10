package com.cricketgame;

public class Team {
    String name;
    int score;
    int wickets;
    Player currentBatsman;
    Player nonStriker;
    Player[] players;

    Team(String name) {
        this.name = name;
        this.score = 0;
        this.wickets = 0;
    }

    void play(int overs, Team oppositeTeam) {

        currentBatsman = players[0];
        nonStriker = players[1];
        int playerFactor = 9;

        System.out.println("\n ==> " + name + " is Batting");

        for (int i = 0; i < overs; i++) {

            Player currentBowler = oppositeTeam.players[(int) (Math.random() * 1000) % 11];
            System.out.println("\n" + currentBowler.name + " is Bowling\n");

            for (int j = 0; j <= 6; j++) {

                int scored = (int) (Math.random() * 1000) % playerFactor;

                if (scored == 5) continue;

                if (scored >= 7) {
                    wickets += 1;
                    System.out.println((currentBatsman.name) + " is out with score of " + (currentBatsman.score));
                    //                System.out.print("\r");
                    if (wickets == 10) {
                        return;
                    }
                    currentBatsman = players[wickets + 1];
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

                score += scored;
//                System.out.println(score);
            }
        }
    }

    void getStats() {
        System.out.println("\nStats for " + name);
        System.out.println("Name \t Wickets \t Runs");
        for (Player p : players) {
            if (p == currentBatsman || p == nonStriker) {
                System.out.println("* " + p.name + "\t" + p.wicket + "\t\t" + p.score);
            } else {
                System.out.println(p.name + "  \t" + p.wicket + "\t\t" + p.score);
            }

        }
    }

    void getPlayers() {
        //fetch Players from database
        players = new Player[11];

        for (int i = 0; i < 11; i++) {
            players[i] = new Player("player " + (i + 1), 10, 10, 10);
        }

    }

    int updatePlayerFactor(Player batsman, Player bowler) {
        // will update on the basis of skills of batsman and bowler
        return 9;
    }
}
