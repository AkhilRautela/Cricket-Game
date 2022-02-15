package com.cricketgame.service;

import com.cricketgame.models.Match;
import com.cricketgame.models.Player;
import com.cricketgame.models.Team;

public class MatchService {
    Match m;

    public void getStats(Team team) {
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

    public void getResults() {
        getStats(m.team1);
        getStats(m.team2);
        System.out.println("\nScore of " + m.team1.name + " is " + m.team1.score + " with wickets = " + m.team1.wickets);
        System.out.println("Score of " + m.team2.name + " is " + m.team2.score + " with wickets = " + m.team2.wickets);
        if (m.team1.score == m.team2.score) {
            System.out.println("Draw between the teams");
        } else if (m.team1.score > m.team2.score) {
            System.out.println("\n" + m.team1.name + " wins the game");
        } else {
            System.out.println("\n" + m.team2.name + " wins the game");
        }
    }

    public void start(Team team1, Team team2, int overs) {
        m = new Match.getMatchDetails().setTeam1(team1).setTeam2(team2).setOvers(overs).build();
        team1.getPlayers();
        team2.getPlayers();
        InningService innings = new InningService();
        innings.startInning(overs, team1, team2);
        innings.startInning(overs, team2, team1);
        getResults();
    }
}
