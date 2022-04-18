package com.cricketgame.service;

import com.cricketgame.exceptions.CricketGameException;
import com.cricketgame.models.Team;
import com.cricketgame.models.entity.PlayerDetails;
import com.cricketgame.models.entity.TeamDetails;
import com.cricketgame.models.enums.Teams;
import com.cricketgame.models.response.TeamInfo;
import com.cricketgame.repositories.PlayerDetailRepo;
import com.cricketgame.repositories.TeamDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeamServiceImpl {

    @Autowired
    TeamDetailRepo teamDetailsRepository;
    @Autowired
    PlayerDetailRepo playerDetailsRepository;
    /**
     * Get information of the team with the given teamname.
     * @param teamName
     * @return
     */
    public ResponseEntity<Object> getInfoOfTheTeam(String teamName) {
        try {
            TeamDetails teamDetails = teamDetailsRepository.findByName(teamName.toUpperCase());
            TeamInfo teamInfo = new TeamInfo();
            teamInfo.setTeamId(teamDetails.getTeamId());
            teamInfo.setTeamName(teamDetails.getName());
            teamInfo.setPlayerDetails((ArrayList<PlayerDetails>) playerDetailsRepository.findByTeamId(teamDetails.getTeamId()));
            return ResponseEntity.ok().body(teamInfo);
        }
        catch (NullPointerException nullPointerException){
            throw new CricketGameException("Team not present in database", HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            throw new CricketGameException("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Team createTeam(int teamId) {
        ArrayList<PlayerDetails> playerDetails = (ArrayList<PlayerDetails>) playerDetailsRepository.findByTeamId(teamId);
        TeamDetails teamDetails = teamDetailsRepository.findByTeamId(teamId);
        Team team = new Team();
        team.setName(Teams.valueOf(teamDetails.getName()));
        team.setPlayerDetails(playerDetails);
        return team;
    }
}
