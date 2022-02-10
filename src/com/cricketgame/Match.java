package com.cricketgame;

public class Match {

    Team team1;
    Team team2;
    int overs;

    Match(getMatchDetails MatchDetails) {
        this.team1 = MatchDetails.team1;
        this.team2 = MatchDetails.team2;
        this.overs = MatchDetails.overs;
    }

    void startMatch() {
        team1.getPlayers();
        team2.getPlayers();
        team1.play(overs, team2);
        team2.play(overs, team1);
    }

    void getResults() {
        team1.getStats();
        team2.getStats();
        System.out.println("\nScore of " + team1.name + " is " + team1.score + " with wickets = " + team1.wickets);
        System.out.println("Score of " + team2.name + " is " + team2.score + " with wickets = " + team2.wickets);
        if (team1.score == team2.score) {
            System.out.println("Draw between the teams");
        } else if (team1.score > team2.score) {
            System.out.println("\n" + team1.name + " wins the game");
        } else {
            System.out.println("\n" + team2.name + " wins the game");
        }
    }


    public static class getMatchDetails {
        Team team1;
        Team team2;
        int overs = 20;

        getMatchDetails SetTeam1(Team team1) {
            this.team1 = team1;
            return this;
        }

        getMatchDetails SetTeam2(Team team2) {
            this.team2 = team2;
            return this;
        }

        getMatchDetails SetOvers(int overs) {
            this.overs = overs;
            return this;
        }

        Match build() {
            return new Match(this);
        }
    }

}
