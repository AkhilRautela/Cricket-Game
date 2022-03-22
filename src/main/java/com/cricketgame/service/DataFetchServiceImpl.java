package com.cricketgame.service;

import com.cricketgame.exceptions.CricketGameException;
import com.cricketgame.models.Inning;
import com.cricketgame.models.Player;
import com.cricketgame.models.Team;
import com.cricketgame.models.databasemodels.Match.MatchDetails;
import com.cricketgame.models.databasemodels.Match.PlayerDetails;
import com.cricketgame.models.databasemodels.Match.TeamDetails;
import com.cricketgame.models.databasemodels.Player.PlayerDetailsInAnMatch;
import com.cricketgame.models.databasemodels.Team.PlayerInfo;
import com.cricketgame.models.databasemodels.Team.TeamInfo;
import com.cricketgame.repositories.InningRepository;
import com.cricketgame.repositories.MatchRepository;
import com.cricketgame.repositories.PlayerRepository;
import com.cricketgame.repositories.TeamRepository;
import com.cricketgame.utils.InningUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataFetchServiceImpl {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    InningRepository inningRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    MatchRepository matchRepository;

    /**
     * Get innings for provided match ID.
     *
     * @param matchId
     * @return
     * @throws SQLException
     */
    public ArrayList<Inning> getInningsForMatch(int matchId) throws SQLException {

        ArrayList<Inning> innings = new ArrayList<Inning>();
        ArrayList<Integer> inningsId = inningRepository.getInnings(matchId);
        if (inningsId.size() < 2) {
            throw new CricketGameException("Match with given Id is not found", HttpStatus.NOT_FOUND);
        }
        innings.add(inningRepository.createInnings(inningsId.get(0)));
        innings.add(inningRepository.createInnings(inningsId.get(1)));
        return innings;
    }

    /**
     * Fetch Match Details for provided match ID.
     *
     * @param matchId
     * @return
     */
    public ResponseEntity<Object> fetchMatchDetails(int matchId) {
        ArrayList<Inning> innings;
        try {
            innings = getInningsForMatch(matchId);
            if (innings.size() != 2) {
                throw new CricketGameException("Match with the given Id is not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Object>(fillMatchDetails(matchId, innings.get(0), innings.get(1)), HttpStatus.OK);
        } catch (SQLException e) {
            throw new CricketGameException("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    /**
     * Fill the MatchDetails class object to return in response object.
     *
     * @param matchId
     * @param inning1
     * @param inning2
     * @return
     */
    private MatchDetails fillMatchDetails(int matchId, Inning inning1, Inning inning2) throws SQLException {
        MatchDetails matchDetails = new MatchDetails();
        matchDetails.setMatchId(matchId);
        matchDetails.setOvers(matchRepository.getOvers(matchId));
        matchDetails.setTeamDetails(new ArrayList<TeamDetails>());
        try {
            Team Team1 = inning1.getBattingTeam();
            Team Team2 = inning1.getBowlingTeam();
            matchDetails.getTeamDetails().add(getTeamDetails(Team1, inning1, inning2));
            matchDetails.getTeamDetails().add(getTeamDetails(Team2, inning2, inning1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchDetails;
    }

    /**
     * Get team details for current team in provided innings.
     *
     * @param team
     * @param inning1
     * @param inning2
     * @return
     */
    private TeamDetails getTeamDetails(Team team, Inning inning1, Inning inning2) {

        TeamDetails teamDetails = new TeamDetails();
        teamDetails.setTeamName(team.getName().toString());
        teamDetails.setTotalScore(InningUtils.getScore(inning1));
        teamDetails.setTotalWicketTaken(InningUtils.getTotalWickets(inning2));
        teamDetails.setPlayerDetails(new ArrayList<PlayerDetails>());

        for (Player player : team.getPlayers()) {
            teamDetails.getPlayerDetails().add(getPlayerDetails(player, inning1, inning2));
        }
        return teamDetails;
    }

    /**
     * Get player details in provided innings.
     *
     * @param player
     * @param inning1
     * @param inning2
     * @return
     */
    private PlayerDetails getPlayerDetails(Player player, Inning inning1, Inning inning2) {
        PlayerDetails playerdetail = new PlayerDetails();
        playerdetail.setName(player.getName());
        playerdetail.setScoreInTheMatch(InningUtils.getScoreOfPlayer(inning1, player));
        playerdetail.setWicketTakenInTheMatch(InningUtils.getWicketsTakenByPlayer(inning2, player));
        Player bowler = InningUtils.getWicketTaker(inning1, player);
        if (bowler == null) {
            playerdetail.setOut(false);
            playerdetail.setBowlerName("None");
        } else {
            playerdetail.setOut(true);
            playerdetail.setBowlerName(bowler.getName());
        }
        return playerdetail;
    }

    /**
     * Get player Stats for an Match.
     *
     * @param matchId
     * @param playerId
     * @return
     */
    public ResponseEntity<Object> getPlayerStatsInMatch(int matchId, int playerId) {
        Map<String, String> response = new HashMap<String, String>();
        try {
            ArrayList<Inning> innings = getInningsForMatch(matchId);
            Team team1 = innings.get(0).getBattingTeam();
            Team team2 = innings.get(1).getBowlingTeam();
            if (playerRepository.isPlayerPresent(team1, playerId) || playerRepository.isPlayerPresent(team2, playerId)) {
                return fillPlayerDetails(playerId, innings);
            } else {
                throw new CricketGameException("Player Not Present in the Match", HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new CricketGameException("Match with the given id is not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Fill player Details object to return to the response entity.
     *
     * @param playerId
     * @param innings
     * @return
     */
    private ResponseEntity<Object> fillPlayerDetails(int playerId, ArrayList<Inning> innings) {
        PlayerDetailsInAnMatch playerDetailsInAnMatch = new PlayerDetailsInAnMatch();
        try {
            Player player = playerRepository.createPlayer(playerId);
            playerDetailsInAnMatch.setWicketTaken(InningUtils.getAllPlayersWhoseWicketsIsTaken(innings.get(0), player));
            playerDetailsInAnMatch.setBallsPlayed(InningUtils.getAllBallsPlayed(player, innings.get(1)));
            return new ResponseEntity<Object>(playerDetailsInAnMatch, HttpStatus.OK);
        } catch (SQLException sqlException) {
            throw new CricketGameException("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get information of the team with the given teamname.
     *
     * @param teamName
     * @return
     */
    public ResponseEntity<Object> getInfoOfTheTeam(String teamName) {
        try {
            int teamId = teamRepository.getTeamId(teamName);
            if (teamId == -1) {
                throw new CricketGameException("Team not present in database", HttpStatus.NOT_FOUND);
            }
            Team team = teamRepository.createTeam(teamId);
            TeamInfo teamInfo = new TeamInfo();
            teamInfo.setId(teamId);
            teamInfo.setName(team.getName().toString());
            teamInfo.setPlayerInfos(getPlayersInfo(teamId, team));
            return ResponseEntity.ok().body(teamInfo);
        } catch (SQLException sqlException) {
            throw new CricketGameException("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get players information for the given team.
     *
     * @param teamId
     * @param team
     * @return
     * @throws SQLException
     */
    public ArrayList<PlayerInfo> getPlayersInfo(int teamId, Team team) throws SQLException {

        ArrayList<PlayerInfo> playerInfos = new ArrayList<PlayerInfo>();

        for (Player player : team.getPlayers()) {
            PlayerInfo playerInfo = new PlayerInfo();
            playerInfo.setId(playerRepository.getPlayerId(teamId, player.getName()));
            playerInfo.setName(player.getName());
            playerInfo.setRating(player.getRating());
            playerInfo.setType(player.getPlayertype().toString());
            playerInfos.add(playerInfo);
        }

        return playerInfos;
    }

    /**
     * Get a player details by using player's ID.
     *
     * @param playerId
     * @return
     */
    public ResponseEntity<Object> getPlayerDetails(int playerId) {
        try {
            Player player = playerRepository.createPlayer(playerId);
            return new ResponseEntity<Object>(player, HttpStatus.OK);
        } catch (SQLException sqlException) {
            throw new CricketGameException("Player With the given Id does not exist", HttpStatus.NOT_FOUND);
        }
    }


}
