package com.cricketgame.service;

import com.cricketgame.models.Inning;
import com.cricketgame.models.Match;
import com.cricketgame.models.Player;
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

    public void showScoreBoard(){
        System.out.println("\nStats for "  + inning1.battingTeam.name);
        System.out.println("Player Name \t   PlayerType \t  Runs  Wickets");
        for(int i = 0; i < 11; i++){
            Player currentPlayer = inning1.battingTeam.players.get(i);
            System.out.println(currentPlayer.name + "\t\t\t" + currentPlayer.playertype + "\t\t\t" + inning1.scoreOfPlayers.get(i) + "\t\t" + inning2.wicketsTaken.get(i));
        }
        System.out.println("\nStats for "  + inning2.battingTeam.name);
        System.out.println("Player Name \t   PlayerType \t  Runs  Wickets");
        for(int i = 0; i < 11; i++){
            Player currentPlayer = inning2.battingTeam.players.get(i);
            System.out.println(currentPlayer.name + "\t\t\t" + currentPlayer.playertype + "\t\t\t" + inning2.scoreOfPlayers.get(i) + "\t\t" + inning1.wicketsTaken.get(i));
        }

    }


    public void start(Match m) {

        InningService inningService = new InningService();

        inning1 = new Inning(m.team1, m.team2 , m.overs,false , 0);
        inningService.startInning(inning1);

        inning2 = new Inning(m.team2, m.team1, m.overs, true , inning1.score);
        inningService.startInning(inning2);

    }
}
