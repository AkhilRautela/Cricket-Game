package com.cricketgame.controllers;

import com.cricketgame.database.DatabaseService;
import com.cricketgame.models.Inning;
import com.cricketgame.models.Player;
import com.cricketgame.models.Team;
import com.cricketgame.repositories.PlayerRepository;
import com.cricketgame.repositories.TeamRepository;
import com.cricketgame.service.DataFetchService;
import com.cricketgame.utils.MatchUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/api/player")
public class PlayerController {

    @RequestMapping("/playerdetails/{playerId}/{matchId}")
    Map<String, String> getPlayerDetailsForAnMatch(@PathVariable(value = "playerId") int playerId, @PathVariable(value = "matchId") int matchId) throws SQLException {
        DataFetchService dfs = new DataFetchService();
        dfs.getMatch(matchId);
        Inning inning1 = dfs.getInning1();
        Inning inning2 = dfs.getInning2();
        Team team1 = inning1.getBattingTeam();
        Team team2 = inning2.getBowlingTeam();
        if(checkPlayerPresent(team1,playerId) || checkPlayerPresent(team2,playerId)) {
            return fillPlayerDetails(inning2, inning1, playerId);
        }
        else{
            Map <String, String> response = new HashMap<String, String>();
            response.put("status" , "0");
            response.put("reason" , "Player Not present in the teams of given match ID");
            return response;
        }
    }

    private boolean checkPlayerPresent(Team team, int playerId) throws SQLException {
        int teamId = TeamRepository.getTeamId(String.valueOf(team.getName()));
        String query = "SELECT * FROM PLAYERDETAILS WHERE teamid = " + teamId + " and playerid = " + playerId;
        ResultSet result = DatabaseService.getResult(query);
        int noOfRows = 0;
        while(result.next()){
            noOfRows += 1;
        }
        return noOfRows == 1;
    }

    private Map<String, String> fillPlayerDetails(Inning inning2, Inning inning1, int playerId) throws SQLException {
        Map <String, String> response = new HashMap<String, String>();
        Player player = PlayerRepository.createPlayer(playerId);
        int playerScore = MatchUtils.getScoreOfPlayer(inning1, player) + MatchUtils.getScoreOfPlayer(inning2, player);
        int wicketTaken = MatchUtils.getWicketsTakenByPlayer(inning1, player) + MatchUtils.getWicketsTakenByPlayer(inning2,player);
        try {
            response.put("status", "1");
            response.put("playerId", String.valueOf(playerId));
            response.put("name", player.getName());
            response.put("score", String.valueOf(playerScore));
            response.put("wicket taken", String.valueOf(wicketTaken));
        }
        catch (Exception e){
            response.put("status","0");
        }
        return  response;
    }


}
