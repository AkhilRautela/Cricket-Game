package com.cricketgame.service;

import com.cricketgame.database.DatabaseService;
import com.cricketgame.models.Inning;
import com.cricketgame.models.Player;
import com.cricketgame.models.Team;
import com.cricketgame.repositories.InningRepository;
import com.cricketgame.repositories.MatchRepository;
import com.cricketgame.repositories.TeamRepository;
import com.cricketgame.utils.MatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MatchService {

    @Autowired
    InningService inningService;
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    InningRepository inningRepository;
    @Autowired
    TeamRepository teamRepository;
    Inning inning1;
    Inning inning2;

    public void getResults() {
        int team1score = MatchUtils.getScore(inning1);
        int team2score = MatchUtils.getScore(inning2);
        System.out.println("\nScore of " + inning1.getBattingTeam().getName() + " is " + team1score + " with wickets = " + MatchUtils.getTotalWickets(inning1));
        System.out.println("Score of " + inning2.getBattingTeam().getName() + " is " + team2score + " with wickets = " + MatchUtils.getTotalWickets(inning2));
        if (team1score == team2score) {
            System.out.println("Draw between the teams");
        } else if (team1score > team2score) {
            System.out.println("\n" + inning1.getBattingTeam().getName() + " wins the game");
        } else {
            System.out.println("\n" + inning1.getBowlingTeam().getName() + " wins the game");
        }
    }

    public void showScoreBoard(){
        System.out.println("\nStats for "  + inning1.getBattingTeam().getName());
        System.out.println("Player Name \t   PlayerType \t  Runs  Wickets");
        for(int i = 0; i < 11; i++){
            Player currentPlayer = inning1.getBattingTeam().getPlayers().get(i);
            System.out.println(currentPlayer.getName() + "\t\t\t" + currentPlayer.getPlayertype() + "\t\t\t" + MatchUtils.getScoreOfPlayer(inning1,currentPlayer) + "\t\t" + MatchUtils.getWicketsTakenByPlayer(inning2,currentPlayer));
        }
        System.out.println("\nStats for "  + inning2.getBattingTeam().getName());
        System.out.println("Player Name \t   PlayerType \t  Runs  Wickets");
        for(int i = 0; i < 11; i++){
            Player currentPlayer = inning2.getBattingTeam().getPlayers().get(i);
            System.out.println(currentPlayer.getName() + "\t\t\t" + currentPlayer.getPlayertype() + "\t\t\t" + MatchUtils.getScoreOfPlayer(inning2,currentPlayer) + "\t\t" + MatchUtils.getWicketsTakenByPlayer(inning1,currentPlayer));
        }

    }


    public void start(Team team1, Team team2, int overs) throws SQLException {

        matchRepository.createMatch(overs);

        inning1 = new Inning(team1, team2 , overs,false , 0);
        inningRepository.createInning(team1,team2);
        inningService.startInning(inning1);
        inningRepository.insertInningData(inning1);

        inning2 = new Inning(team2, team1, overs, true , MatchUtils.getScore(inning1));
        inningRepository.createInning(team2,team1);
        inningService.startInning(inning2);
        inningRepository.insertInningData(inning2);

    }

    public Inning getInning1() {
        return inning1;
    }

    public void setInning1(Inning inning1) {
        this.inning1 = inning1;
    }

    public Inning getInning2() {
        return inning2;
    }

    public void setInning2(Inning inning2) {
        this.inning2 = inning2;
    }


    public ResponseEntity<Object> startMatch(String team1Name, String team2Name, int overs){

        Map <String, String> response = new HashMap<String, String>();
      try{
        Team team1 = teamRepository.createTeam(teamRepository.getTeamId(team1Name));
        Team team2 = teamRepository.createTeam(teamRepository.getTeamId(team2Name));
            start(team1, team2, overs);
            showScoreBoard();
            getResults();
            response.put("status","1");
            response.put("matchId", String.valueOf(matchRepository.matchId));
        }
        catch (Exception e){
            response.put("Status", "0");
            e.printStackTrace();
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}
