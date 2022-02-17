package com.cricketgame.service;

import com.cricketgame.models.Inning;
import com.cricketgame.models.Match;
import com.cricketgame.models.Team;

public class MatchService {
    Match m;

    public void getResults(Inning inning1, Inning inning2) {
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


    public void start(Team team1, Team team2, int overs) {
        m = new Match.getMatchDetails().setTeam1(team1).setTeam2(team2).setOvers(overs).build();
        InningService innings1 = new InningService(overs, team1, team2, false , 0);
        innings1.startInning(); // parameter about previous inning done or not
        InningService innings2 = new InningService(overs, team2, team1, true , innings1.inning.score);
        innings2.startInning();
        getResults(innings1.inning, innings2.inning);
    }
}
