package com.cricketgame.controllers;


import com.cricketgame.service.DataFetchServiceImpl;
import com.cricketgame.service.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.sql.SQLException;

@RestController
@Component
@RequestMapping("/api/match")
public class MatchController {

    @Autowired
    DataFetchServiceImpl dataFetchService;
    @Autowired
    MatchServiceImpl matchService;


    @GetMapping("/matchdetails/{id}")
    public ResponseEntity<Object> getMatchDetails(@PathVariable(value = "id") int id) throws SQLException {
        return dataFetchService.fetchMatchDetails(id);
    }

    @GetMapping("/startMatch/team1name/{team1name}/team2name/{team2name}/overs/{overs}")
    public ResponseEntity<Object> startMatch(@PathVariable(value = "team1name") String team1Name, @PathVariable(value = "team2name") String team2Name, @PathVariable(value = "overs") int overs) throws SQLException {
        return matchService.startMatch(team1Name, team2Name, overs);
    }


}
