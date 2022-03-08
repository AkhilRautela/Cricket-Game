package com.cricketgame.service;

import com.cricketgame.constants.Constants;
import com.cricketgame.database.DatabaseService;
import com.cricketgame.models.*;
import com.cricketgame.models.databasemodels.MatchDetails;
import com.cricketgame.models.databasemodels.PlayerDetails;
import com.cricketgame.models.databasemodels.TeamDetails;
import com.cricketgame.models.enums.BallType;
import com.cricketgame.repositories.InningRepository;
import com.cricketgame.repositories.PlayerRepository;
import com.cricketgame.repositories.TeamRepository;
import com.cricketgame.utils.MatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataFetchService {

    Inning inning1;
    Inning inning2;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    InningRepository inningRepository;
    @Autowired
    PlayerRepository playerRepository;

    public void getMatch(int matchId) throws SQLException {
        ArrayList<Integer> inningsId = inningRepository.getInnings(matchId);
        inning1 = createInnings(inningsId.get(0));
        inning2 = createInnings(inningsId.get(1));
    }

    public Inning createInnings(int inningId) throws SQLException {
        String query = "SELECT * FROM INNINGDETAILS WHERE INNINGID = " + inningId;
        ResultSet resultSet = DatabaseService.getResult(query);
        resultSet.next();
        Team battingTeam = teamRepository.createTeam(resultSet.getInt(2));
        Team bowlingTeam = teamRepository.createTeam(resultSet.getInt(3));
        Inning inning = new Inning(battingTeam,bowlingTeam, Constants.ANY_CONSTANT,true,Constants.ANY_CONSTANT);
        ArrayList<Over> overs = createOvers(inningId);
        inning.setOvers(overs);
        return inning;
    }

    private ArrayList<Over> createOvers(int inningId) throws SQLException {
        String query = "SELECT * FROM OVERDETAILS WHERE INNINGID = " + inningId;
        ResultSet resultSet = DatabaseService.getResult(query);
        ArrayList <Over> overs = new ArrayList<Over>();

        while(resultSet.next()){
            int overId = resultSet.getInt(1);
            int bowlerId = resultSet.getInt(2);
            Player bowler = playerRepository.createPlayer(bowlerId);
            Over over = new Over();
            ArrayList <Ball> balls = createBalls(overId);
            over.setBalls(balls);
            over.setBowler(bowler);
            overs.add(over);
        }

        return overs;
    }

    private ArrayList<Ball> createBalls(int overId) throws SQLException {

        String query = "SELECT * FROM BALLDETAILS WHERE OVERID = " + overId;
        ResultSet resultSet = DatabaseService.getResult(query);
        ArrayList <Ball> balls = new ArrayList<Ball>();

        while(resultSet.next()){
            int strikerId = resultSet.getInt(2);
            Player striker = playerRepository.createPlayer(strikerId);
            Ball ball = new Ball();
            ball.setRunsOnTheBall(resultSet.getInt(3));
            ball.setBallType(BallType.valueOf(resultSet.getString(4)));
            ball.setStriker(striker);
            balls.add(ball);
        }

        return balls;
    }

    public void getResults() {
        MatchService matchService = new MatchService();
        matchService.setInning1(inning1);
        matchService.setInning2(inning2);
        matchService.showScoreBoard();
        matchService.getResults();
    }

    public ResponseEntity<Object> fetchMatchDetails(int matchId) throws SQLException {
        getMatch(matchId);
        return new ResponseEntity<Object>(fillMatchDetails(matchId), HttpStatus.OK);
    }

    private MatchDetails fillMatchDetails(int matchId) {
        MatchDetails matchDetails = new MatchDetails();
        matchDetails.setMatchId(matchId);
        matchDetails.setOvers(inning1.getTotalOvers());
        matchDetails.setTeamDetails(new ArrayList<TeamDetails>());
        try {
            Team Team1 = inning1.getBattingTeam();
            Team Team2 = inning1.getBowlingTeam();
            matchDetails.getTeamDetails().add(getTeamDetails(Team1,inning1,inning2));
            matchDetails.getTeamDetails().add(getTeamDetails(Team2,inning2,inning1));
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
            teamDetails.getPlayerDetails().add(getPlayerDetails(player,inning1,inning2));
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

    public ResponseEntity<Object> getPlayerDetailsForMatch(int matchId, int playerId) throws SQLException {
        getMatch(matchId);
        Team team1 = inning1.getBattingTeam();
        Team team2 = inning2.getBowlingTeam();
        if(checkPlayerPresent(team1,playerId) || checkPlayerPresent(team2,playerId)) {
            return fillPlayerDetails(inning2, inning1, playerId);
        }
        else{
            Map<String, String> response = new HashMap<String, String>();
            response.put("status" , "0");
            response.put("reason" , "Player Not present in the teams of given match ID");
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
    }

    private boolean checkPlayerPresent(Team team, int playerId) throws SQLException {
        int teamId = teamRepository.getTeamId(String.valueOf(team.getName()));
        String query = "SELECT * FROM PLAYERDETAILS WHERE teamid = " + teamId + " and playerid = " + playerId;
        ResultSet result = DatabaseService.getResult(query);
        int noOfRows = 0;
        while(result.next()){
            noOfRows += 1;
        }
        return noOfRows == 1;
    }

    private ResponseEntity<Object> fillPlayerDetails(Inning inning2, Inning inning1, int playerId) throws SQLException {
        Map <String, String> response = new HashMap<String, String>();
        Player player = playerRepository.createPlayer(playerId);
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
        return  new ResponseEntity<Object>(response, HttpStatus.OK);
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
}
