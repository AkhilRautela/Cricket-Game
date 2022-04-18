package com.cricketgame.service;

import com.cricketgame.exceptions.CricketGameException;
import com.cricketgame.models.entity.InningDetails;
import com.cricketgame.models.entity.MatchDetails;
import com.cricketgame.models.entity.PlayerDetails;
import com.cricketgame.models.response.MatchInfo;
import com.cricketgame.models.response.PlayerDetailsInMatch;
import com.cricketgame.models.response.TeamDetailsInMatch;
import com.cricketgame.repositories.InningDetailRepo;
import com.cricketgame.repositories.MatchDetailRepo;
import com.cricketgame.repositories.PlayerDetailRepo;
import com.cricketgame.repositories.TeamDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class MatchServiceImpl {

    @Autowired
    InningServiceImpl inningService;
    @Autowired
    PlayerServiceImpl playerService;
    @Autowired
    IdGenerator idGenerator;

    @Autowired
    MatchDetailRepo matchDetailsRepository;
    @Autowired
    TeamDetailRepo teamDetailsRepository;
    @Autowired
    PlayerDetailRepo playerDetailsRepository;
    @Autowired
    InningDetailRepo inningDetailsRepository;



    /**
     * Create innings and starts the match.
     *
     * @param team1Name
     * @param team2Name
     * @param overs
     * @throws SQLException
     */
    public int startGame(String team1Name, String team2Name, int overs) throws SQLException, IOException {

        int team1Id, team2Id;
        MatchDetails matchDetails = new MatchDetails();
        matchDetails.setMatchId(idGenerator.getNextMatchId());
        matchDetails.setTotalOvers(overs);
        MatchDetails matchDetailSaved = matchDetailsRepository.save(matchDetails);

        try {
            team1Id = teamDetailsRepository.findByName(team1Name.toUpperCase()).getTeamId();
        } catch (NullPointerException nullPointerException) {
            throw new CricketGameException(team1Name + " teamname is not found in database", HttpStatus.NOT_FOUND);
        }
        try {
            team2Id = teamDetailsRepository.findByName(team2Name.toUpperCase()).getTeamId();
        } catch (NullPointerException nullPointerException) {
            throw new CricketGameException(team2Name + " teamname is not found in database", HttpStatus.NOT_FOUND);
        }

        int matchId = matchDetailSaved.getMatchId();
        inningService.createAndStartInnings(matchId, team1Id, team2Id, overs);
        return matchId;

    }

    /**
     * Handle starting the match by api call.
     *
     * @param team1Name
     * @param team2Name
     * @param overs
     * @return
     */
    public ResponseEntity<Object> startMatch(String team1Name, String team2Name, int overs) {
        Map<String, String> response = new HashMap<String, String>();
        int matchId = 0;
        try {
            matchId = startGame(team1Name, team2Name, overs);
        } catch (SQLException | IOException e) {
            throw new CricketGameException("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("status", "1");
        response.put("matchId", String.valueOf(matchId));
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> fetchMatchDetails(int matchId) {
        ArrayList<InningDetails> inningDetails = (ArrayList<InningDetails>) inningDetailsRepository.findByMatchId(matchId);
        try{
            int team1Id = inningDetails.get(0).getBattingTeamId();
            int team2Id = inningDetails.get(0).getBowlingTeamId();
            ArrayList <TeamDetailsInMatch> teamDetailsInMatches = new ArrayList<>();
            teamDetailsInMatches.add(fetchTeamDetailsInMatch(matchId, team1Id));
            teamDetailsInMatches.add(fetchTeamDetailsInMatch(matchId, team2Id));
            MatchInfo matchInfo = new MatchInfo();
            matchInfo.setMatchId(matchId);
            matchInfo.setTeamDetailsInMatches(teamDetailsInMatches);
            return new ResponseEntity<Object>(matchInfo, HttpStatus.OK);
        }
        catch (NullPointerException nullPointerException) {
            throw new CricketGameException("Match with given Id is not found", HttpStatus.NOT_FOUND);
        }
        catch (IndexOutOfBoundsException Exception){
            throw new CricketGameException("Match with given Id is not found", HttpStatus.NOT_FOUND);
        }

    }

    private TeamDetailsInMatch fetchTeamDetailsInMatch(int matchId, int teamId) {
        ArrayList <PlayerDetails> playerDetails = (ArrayList<PlayerDetails>) playerDetailsRepository.findByTeamId(teamId);
        TeamDetailsInMatch teamDetailsInMatch = new TeamDetailsInMatch();
        ArrayList <PlayerDetailsInMatch> playersDetailsInMatch = new ArrayList<>();
        for(PlayerDetails playerDetail : playerDetails){
            playersDetailsInMatch.add(playerService.getPlayerDetailsInMatch(matchId, playerDetail.getPlayerId()));
        }
        teamDetailsInMatch.setTeamId(teamId);
        teamDetailsInMatch.setPlayerDetailsInMatches(playersDetailsInMatch);
        return teamDetailsInMatch;
    }
}
