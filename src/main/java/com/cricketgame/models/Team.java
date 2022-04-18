package com.cricketgame.models;

import com.cricketgame.models.entity.PlayerDetails;
import com.cricketgame.models.enums.Teams;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Team {
    private Teams name;
    private ArrayList<PlayerDetails> playerDetails = new ArrayList<>();
}
