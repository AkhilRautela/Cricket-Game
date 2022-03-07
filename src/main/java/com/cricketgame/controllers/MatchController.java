package com.cricketgame.controllers;


import com.cricketgame.models.Inning;
import com.cricketgame.models.Team;
import com.cricketgame.repositories.MatchRepository;
import com.cricketgame.repositories.TeamRepository;
import com.cricketgame.service.DataFetchService;
import com.cricketgame.service.MatchService;
import com.cricketgame.utils.MatchUtils;
import org.springframework.validation.annotation.ValidationAnnotationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    @RequestMapping("/matchdetails/{id}")
    public Map<String,String> getMatchDetails(@PathVariable(value = "id") int id) throws SQLException {
        DataFetchService dataFetchService = new DataFetchService();
        dataFetchService.getMatch(id);
        Inning inning1 = dataFetchService.getInning1();
        Inning inning2 = dataFetchService.getInning2();
        return fillMatchDetails(inning1,inning2,id);
    }

    private Map<String, String> fillMatchDetails(Inning inning1, Inning inning2, int matchId) {
        Map <String, String> response = new HashMap<String, String>();
        try {
            response.put("status","1");
            response.put("matchId", String.valueOf(matchId));
            response.put("team1", inning1.getBattingTeam().getName().toString());
            response.put("team2", inning1.getBowlingTeam().getName().toString());
            response.put("team1score", String.valueOf(MatchUtils.getScore(inning1)));
            response.put("team2score", String.valueOf(MatchUtils.getScore(inning2)));
            response.put("team1wickets", String.valueOf(MatchUtils.getTotalWickets(inning2)));
            response.put("team2wickets", String.valueOf(MatchUtils.getTotalWickets(inning1)));
        }
        catch (Exception e){
            response.put("status", "0");
        }
        return response;
    }

    @RequestMapping("/startMatch/{team1Id}/{team2Id}/{overs}")
    public Map<String, String> startMatch(@PathVariable(value = "team1Id") int team1Id, @PathVariable(value = "team2Id") int team2Id, @PathVariable(value = "overs") int overs) throws SQLException {
        Team team1 = TeamRepository.createTeam(team1Id);
        Team team2 = TeamRepository.createTeam(team2Id);
        Map <String, String> response = new HashMap<String, String>();
        try {
            MatchService match = new MatchService();
            match.start(team1, team2, overs);
            match.showScoreBoard();
            match.getResults();
            response.put("status","1");
            response.put("matchId", String.valueOf(MatchRepository.matchId));
        }
        catch (Exception e){
            response.put("Status", "0");
            e.printStackTrace();
        }
        return response;
    }

}
