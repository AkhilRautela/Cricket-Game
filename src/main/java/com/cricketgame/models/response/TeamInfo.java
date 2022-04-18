package com.cricketgame.models.response;

import com.cricketgame.models.entity.PlayerDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TeamInfo {
    private int teamId;
    private String teamName;
    private ArrayList<PlayerDetails> playerDetails;
}
