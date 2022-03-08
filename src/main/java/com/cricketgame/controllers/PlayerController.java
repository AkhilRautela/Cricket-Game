package com.cricketgame.controllers;

import com.cricketgame.database.DatabaseService;
import com.cricketgame.models.Inning;
import com.cricketgame.models.Player;
import com.cricketgame.models.Team;
import com.cricketgame.repositories.PlayerRepository;
import com.cricketgame.repositories.TeamRepository;
import com.cricketgame.service.DataFetchService;
import com.cricketgame.utils.MatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/api/player")
@Component
public class PlayerController {

    @Autowired
    DataFetchService dataFetchService;


    @GetMapping("/playerdetails/{playerId}/{matchId}")
    ResponseEntity<Object> getPlayerDetailsForAnMatch(@PathVariable(value = "playerId") int playerId, @PathVariable(value = "matchId") int matchId) throws SQLException {
        return dataFetchService.getPlayerDetailsForMatch(matchId, playerId);
    }


}
