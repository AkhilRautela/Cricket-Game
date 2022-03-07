package com.cricketgame.controllers;


import com.cricketgame.models.Inning;
import com.cricketgame.models.Player;
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

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    @RequestMapping("/matchdetails/{id}")
    public MatchDetails getMatchDetails(@PathVariable(value = "id") int id) throws SQLException {
        DataFetchService dataFetchService = new DataFetchService();
        dataFetchService.getMatch(id);
        Inning inning1 = dataFetchService.getInning1();
        Inning inning2 = dataFetchService.getInning2();
        return fillMatchDetails(inning1,inning2,id);
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

    private MatchDetails fillMatchDetails(Inning inning1, Inning inning2, int matchId) {
        MatchDetails matchDetails = new MatchDetails();
        matchDetails.matchId = matchId;
        matchDetails.teamDetails = new ArrayList<TeamDetails>();
        try {
            Team Team1 = inning1.getBattingTeam();
            Team Team2 = inning1.getBowlingTeam();
            matchDetails.teamDetails.add(getTeamDetails(Team1,inning1,inning2));
            matchDetails.teamDetails.add(getTeamDetails(Team2,inning2,inning1));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return matchDetails;
    }

    private TeamDetails getTeamDetails(Team team, Inning inning1, Inning inning2) {

        TeamDetails teamDetails = new TeamDetails();
        teamDetails.setTeamName(team.getName().toString());
        teamDetails.setTotalScore(MatchUtils.getScore(inning1));
        teamDetails.setTotalWicketTaken(MatchUtils.getTotalWickets(inning2));
        teamDetails.setPlayerDetails(new ArrayList<PlayerDetails>());

        for(Player player : team.getPlayers()){
            teamDetails.playerDetails.add(getPlayerDetails(player,inning1,inning2));
        }
        return teamDetails;
    }

    private PlayerDetails getPlayerDetails(Player player, Inning inning1, Inning inning2) {
        PlayerDetails playerdetail = new PlayerDetails();
        playerdetail.setName(player.getName());
        playerdetail.setScoreInTheMatch(MatchUtils.getScoreOfPlayer(inning1,player));
        playerdetail.setWicketTakenInTheMatch(MatchUtils.getWicketsTakenByPlayer(inning2,player));
        return playerdetail;
    }


    class PlayerDetails{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScoreInTheMatch() {
            return scoreInTheMatch;
        }

        public void setScoreInTheMatch(int scoreInTheMatch) {
            this.scoreInTheMatch = scoreInTheMatch;
        }

        public int getWicketTakenInTheMatch() {
            return wicketTakenInTheMatch;
        }

        public void setWicketTakenInTheMatch(int wicketTakenInTheMatch) {
            this.wicketTakenInTheMatch = wicketTakenInTheMatch;
        }

        int scoreInTheMatch;
        int wicketTakenInTheMatch;
    }

    class TeamDetails{
        String teamName;
        int totalScore;
        int totalWicketTaken;

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public int getTotalWicketTaken() {
            return totalWicketTaken;
        }

        public void setTotalWicketTaken(int totalWicketTaken) {
            this.totalWicketTaken = totalWicketTaken;
        }

        public ArrayList<PlayerDetails> getPlayerDetails() {
            return playerDetails;
        }

        public void setPlayerDetails(ArrayList<PlayerDetails> playerDetails) {
            this.playerDetails = playerDetails;
        }

        ArrayList <PlayerDetails> playerDetails;
    }

    class MatchDetails{
        int matchId;

        public int getMatchId() {
            return matchId;
        }

        public void setMatchId(int matchId) {
            this.matchId = matchId;
        }

        public ArrayList<TeamDetails> getTeamDetails() {
            return teamDetails;
        }

        public void setTeamDetails(ArrayList<TeamDetails> teamDetails) {
            this.teamDetails = teamDetails;
        }

        ArrayList <TeamDetails> teamDetails;
    }
}
