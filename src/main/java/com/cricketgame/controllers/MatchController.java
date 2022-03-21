package com.cricketgame.controllers;

import com.cricketgame.models.databasemodels.Match.MatchInfo;
import com.cricketgame.service.DataFetchServiceImpl;
import com.cricketgame.service.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
@RequestMapping("/api/match")
public class MatchController {

    @Autowired
    DataFetchServiceImpl dataFetchService;
    @Autowired
    MatchServiceImpl matchService;


    @GetMapping("/matchdetails/{id}")
    public ResponseEntity<Object> getMatchDetails(@PathVariable(value = "id") int id) {
        return dataFetchService.fetchMatchDetails(id);
    }

    @PostMapping("/startMatch")
    public ResponseEntity<Object> startMatch(@RequestBody MatchInfo matchinfo) {
        return matchService.startMatch(matchinfo.getTeam1Name(), matchinfo.getTeam2Name(), matchinfo.getOvers());
    }


}
