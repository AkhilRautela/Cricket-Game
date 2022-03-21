package com.cricketgame.service;

import com.cricketgame.constants.Constants;
import com.cricketgame.database.DatabaseServiceImpl;
import com.cricketgame.exceptions.*;
import com.cricketgame.models.*;
import com.cricketgame.models.databasemodels.Match.MatchDetails;
import com.cricketgame.models.databasemodels.Match.PlayerDetails;
import com.cricketgame.models.databasemodels.Match.TeamDetails;
import com.cricketgame.models.databasemodels.Player.PlayerDetailsInAnMatch;
import com.cricketgame.models.databasemodels.Team.PlayerInfo;
import com.cricketgame.models.databasemodels.Team.TeamInfo;
import com.cricketgame.models.enums.BallType;
import com.cricketgame.repositories.InningRepository;
import com.cricketgame.repositories.PlayerRepository;
import com.cricketgame.repositories.TeamRepository;
import com.cricketgame.utils.InningUtils;
import com.cricketgame.utils.MatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataFetchServiceImpl {

    Inning inning1;
    Inning inning2;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    InningRepository inningRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    DatabaseServiceImpl databaseService;
    @Autowired
    MatchServiceImpl matchService;

    public Boolean getMatch(int matchId) throws SQLException {

        ArrayList<Integer> inningsId = inningRepository.getInnings(matchId);
        if (inningsId.size() < 2) {
            throw new MatchNotFoundException("Match with given Id is not found",0);
        };
        inning1 = createInnings(inningsId.get(0));
        inning2 = createInnings(inningsId.get(1));
        return true;

    }

    public Inning createInnings(int inningId) throws SQLException {
        String query = "SELECT * FROM INNINGDETAILS WHERE INNINGID = " + inningId;
        ResultSet resultSet = databaseService.getResult(query);
        resultSet.next();
        Team battingTeam = teamRepository.createTeam(resultSet.getInt(2));
        Team bowlingTeam = teamRepository.createTeam(resultSet.getInt(3));
        Inning inning = new Inning(battingTeam,bowlingTeam, Constants.ANY_CONSTANT,true,Constants.ANY_CONSTANT);
        ArrayList<Over> overs = createOvers(inningId);
        inning.setOvers(overs);
        return inning;
    }

    public ArrayList<Over> createOvers(int inningId) throws SQLException {
        String query = "SELECT * FROM OVERDETAILS WHERE INNINGID = " + inningId;
        ResultSet resultSet = databaseService.getResult(query);
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

    public ArrayList<Ball> createBalls(int overId) throws SQLException {

        String query = "SELECT * FROM BALLDETAILS WHERE OVERID = " + overId;
        ResultSet resultSet = databaseService.getResult(query);
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
        matchService.setInning1(inning1);
        matchService.setInning2(inning2);
        matchService.showScoreBoard();
        matchService.getResults();
    }

    public ResponseEntity<Object> fetchMatchDetails(int matchId) {

        boolean isFound;
        try {
            isFound = getMatch(matchId);
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
        if(isFound == false){
            throw new MatchNotFoundException("Match with the given Id is not found", 0);
        }
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
        Player bowler = InningUtils.isOut(inning1, player);
        if (bowler == null){
            playerdetail.setOut(false);
            playerdetail.setBowler("None");
        }
        else{
            playerdetail.setOut(true);
            playerdetail.setBowler(bowler.getName());
        }
        return playerdetail;
    }

    public ResponseEntity<Object> getPlayerDetailsForMatch(int matchId, int playerId){
        Map<String, String> response = new HashMap<String, String>();
        try {
            getMatch(matchId);
            Team team1 = inning1.getBattingTeam();
            Team team2 = inning2.getBowlingTeam();
            if (checkPlayerPresent(team1, playerId) || checkPlayerPresent(team2, playerId)) {
                return fillPlayerDetails(playerId);
            } else {
                throw new PlayerNotFoundInMatchException("Player Not Present in the Match", 0);
            }
        }
        catch (SQLException e){
            throw new DatabaseErrorException("Match with the given id is not found", 0);
        }
    }

    private boolean checkPlayerPresent(Team team, int playerId) throws SQLException {
        int teamId = teamRepository.getTeamId(String.valueOf(team.getName()));
        String query = "SELECT * FROM PLAYERDETAILS WHERE teamid = " + teamId + " and playerid = " + playerId;
        ResultSet result = databaseService.getResult(query);
        return result.next();
    }

    private ResponseEntity<Object> fillPlayerDetails(int playerId) {
        PlayerDetailsInAnMatch playerDetailsInAnMatch = new PlayerDetailsInAnMatch();
        try{
            Player player = playerRepository.createPlayer(playerId);
            playerDetailsInAnMatch.setWicketTaken(MatchUtils.getPlayersWhoseWicketsIsTaken(inning2, player));
            playerDetailsInAnMatch.setBallsPlayed(MatchUtils.getBallsPlayed(player, inning1));
            return  new ResponseEntity<Object>(playerDetailsInAnMatch, HttpStatus.OK);
        }
        catch (SQLException sqlException){
            throw new DatabaseErrorException();
        }
    }

    public ResponseEntity<Object> getInfoOfTheTeam(String teamName) {
        try {
            int teamId = teamRepository.getTeamId(teamName);
            if (teamId == -1) {
                throw new TeamNotFoundException("Team not present in database", 0);
            }
            Team team = teamRepository.createTeam(teamId);
            TeamInfo teamInfo = new TeamInfo();
            teamInfo.setId(teamId);
            teamInfo.setName(team.getName().toString());
            teamInfo.setPlayerInfos(getPlayersInfo(teamId, team));
            return ResponseEntity.ok().body(teamInfo);
        }
        catch (SQLException sqlException) {
            throw new DatabaseErrorException();
        }
    }

    public ArrayList<PlayerInfo> getPlayersInfo(int teamId, Team team) throws SQLException {

        ArrayList <PlayerInfo> playerInfos = new ArrayList<PlayerInfo>();

        for(Player player : team.getPlayers()){
            PlayerInfo playerInfo = new PlayerInfo();
            playerInfo.setId(playerRepository.getPlayerId(teamId, player.getName()));
            playerInfo.setName(player.getName());
            playerInfo.setRating(player.getRating());
            playerInfo.setType(player.getPlayertype().toString());
            playerInfos.add(playerInfo);
        }

        return playerInfos;
    }

    public ResponseEntity<Object> getPlayerDetails(int playerId) {
        try{
            Player player = playerRepository.createPlayer(playerId);
            return new ResponseEntity<Object>(player,HttpStatus.OK);
        }
        catch(SQLException sqlException){
            throw new PlayerNotFoundException("Player With the given Id does not exist", 0);
        }
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
