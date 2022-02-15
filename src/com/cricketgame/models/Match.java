package com.cricketgame.models;

import com.cricketgame.service.MatchService;

public class Match {

    public Team team1;
    public Team team2;
    int overs;

    Match(getMatchDetails MatchDetails) {
        this.team1 = MatchDetails.team1;
        this.team2 = MatchDetails.team2;
        this.overs = MatchDetails.overs;
    }



    public static class getMatchDetails {
        Team team1;
        Team team2;
        int overs = 20;

        public getMatchDetails setTeam1(Team team1) {
            this.team1 = team1;
            return this;
        }

        public getMatchDetails setTeam2(Team team2) {
            this.team2 = team2;
            return this;
        }

        public getMatchDetails setOvers(int overs) {
            this.overs = overs;
            return this;
        }

        public Match build() {
            return new Match(this);
        }
    }

}
