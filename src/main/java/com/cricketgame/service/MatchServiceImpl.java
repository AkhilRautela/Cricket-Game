package com.cricketgame.service;

import com.cricketgame.exceptions.CricketGameException;
import com.cricketgame.models.Inning;
import com.cricketgame.models.Team;
import com.cricketgame.repositories.InningRepository;
import com.cricketgame.repositories.MatchRepository;
import com.cricketgame.repositories.OverRepository;
import com.cricketgame.repositories.TeamRepository;
import com.cricketgame.utils.InningUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MatchServiceImpl {

    @Autowired
    InningServiceImpl inningService;
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    InningRepository inningRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    OverRepository overRepository;

    /**
     * Create innings and starts the match.
     *
     * @param team1
     * @param team2
     * @param overs
     * @throws SQLException
     */
    public int start(Team team1, Team team2, int overs) throws SQLException {

        Inning inning1;
        Inning inning2;

        int matchId = matchRepository.createMatch(overs);
        int battingTeamId = teamRepository.getTeamId(String.valueOf(team1.getName()));
        int bowlingTeamId = teamRepository.getTeamId(String.valueOf(team2.getName()));

        inning1 = new Inning(team1, team2, overs, false, 0);

        int inning1id = inningRepository.insertInning(matchId, battingTeamId, bowlingTeamId);
        inningService.startInning(inning1);
        overRepository.insertOverData(inning1, inning1id, bowlingTeamId, battingTeamId);

        inning2 = new Inning(team2, team1, overs, true, InningUtils.getScore(inning1));
        int inning2id = inningRepository.insertInning(matchId, bowlingTeamId, battingTeamId);
        inningService.startInning(inning2);
        overRepository.insertOverData(inning2, inning2id, battingTeamId, bowlingTeamId);

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
        Team team1, team2;
        int matchId;
        try {
            team1 = teamRepository.createTeam(teamRepository.getTeamId(team1Name));
        } catch (SQLException e) {
            throw new CricketGameException(team1Name + " teamname is not found in database", HttpStatus.NOT_FOUND);
        }
        try {
            team2 = teamRepository.createTeam(teamRepository.getTeamId(team2Name));
        } catch (SQLException e) {
            throw new CricketGameException(team2Name + " teamname is not found in database", HttpStatus.NOT_FOUND);
        }
        try {
            matchId = start(team1, team2, overs);
        } catch (SQLException e) {
            throw new CricketGameException("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("status", "1");
        response.put("matchId", String.valueOf(matchId));
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}
