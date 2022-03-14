package com.cricketgame.controllers;


import com.cricketgame.service.DataFetchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;


@RestController()
@RequestMapping("/api/player")
@Component
public class PlayerController {

    @Autowired
    DataFetchServiceImpl dataFetchService;

    @GetMapping("/playerdetails/playerid/{playerId}/matchid/{matchId}")
    ResponseEntity<Object> getPlayerDetailsForAnMatch(@PathVariable(value = "playerId") int playerId, @PathVariable(value = "matchId") int matchId) throws SQLException {
        return dataFetchService.getPlayerDetailsForMatch(matchId, playerId);
    }

    @GetMapping("/playerdetails/playerid/{playerId}")
    ResponseEntity<Object> getPlayerDetails(@PathVariable(value = "playerId") int playerId) {
        return dataFetchService.getPlayerDetails(playerId);
    }

}
