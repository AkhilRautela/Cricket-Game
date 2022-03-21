package com.cricketgame.controllers;

import com.cricketgame.service.DataFetchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    DataFetchServiceImpl dataFetchService;

    @GetMapping("/teamname/{teamName}")
    ResponseEntity<Object> getTeamInfo(@PathVariable(value = "teamName") String teamName) {
        return dataFetchService.getInfoOfTheTeam(teamName);
    }
}
