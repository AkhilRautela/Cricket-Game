package com.cricketgame.service;

import com.cricketgame.models.Inning;
import com.cricketgame.models.Match;
import com.cricketgame.models.Team;

public class MatchService {

    Inning inning1, inning2;

    public void getResults(Match m) {
        int team1score = inning1.score;
        int team2score = inning2.score;
        System.out.println("\nScore of " + inning1.battingTeam.name + " is " + inning1.score + " with wickets = " + inning1.wickets);
        System.out.println("Score of " + inning2.battingTeam.name + " is " + inning2.score + " with wickets = " + inning2.wickets);
        if (team1score == team2score) {
            System.out.println("Draw between the teams");
        } else if (team1score > team2score) {
            System.out.println("\n" + m.team1.name + " wins the game");
        } else {
            System.out.println("\n" + m.team2.name + " wins the game");
        }
    }


    public void start(Match m) {

        inning1 = new Inning(m.team1, m.team2 , m.overs,false , 0);
        InningService inningService1 = new InningService();
        inningService1.startInning(inning1);

        inning2 = new Inning(m.team2, m.team1, m.overs, true , inning1.score);
        InningService inningService2 = new InningService();
        inningService2.startInning(inning2);

    }
}
