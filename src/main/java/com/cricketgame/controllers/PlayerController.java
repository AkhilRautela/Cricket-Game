package com.cricketgame.controllers;


import com.cricketgame.service.DataFetchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/api/player")
@Component
public class PlayerController {

    @Autowired
    DataFetchServiceImpl dataFetchService;

    @GetMapping("/playerid/{playerId}/matchid/{matchId}")
    ResponseEntity<Object> getPlayerStatsInMatch(@PathVariable(value = "playerId") int playerId, @PathVariable(value = "matchId") int matchId) {
        return dataFetchService.getPlayerStatsInMatch(matchId, playerId);
    }

    @GetMapping("/playerid/{playerId}")
    ResponseEntity<Object> getPlayerStats(@PathVariable(value = "playerId") int playerId) {
        return dataFetchService.getPlayerDetails(playerId);
    }

}
