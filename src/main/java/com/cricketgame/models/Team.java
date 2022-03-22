package com.cricketgame.models;

import com.cricketgame.models.enums.Teams;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Team {
    private Teams name;
    private ArrayList<Player> players = new ArrayList<>();
}
