package com.cricketgame;

public class Team {
    String name;
    int score;
    int wickets;

    Team(String name) {
        this.name = name;
        this.score = 0;
        this.wickets = 0;
    }

    void play(int overs) {

        int prev = 0;

        System.out.println(name + " is playing");
        for (int i = 0; i <= overs * 6; i++) {
            int scored = (int) (Math.random() * 1000) % 8;
            if (scored == 7) {
                wickets += 1;
                System.out.println("Player " + (wickets) + " is out with score of " + (score - prev));
//                System.out.print("\r");
                if (wickets == 10) {
                    break;
                }
                prev = score;
            }
            if (scored == 6) {
//                System.out.println("Player " + (wickets + 1) + " scored 6");
//                System.out.print("\r");
            }

            score += scored;
        }
    }
}
