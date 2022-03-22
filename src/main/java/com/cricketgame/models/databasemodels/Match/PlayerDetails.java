package com.cricketgame.models.databasemodels.Match;

import lombok.Data;

@Data
public class PlayerDetails {
    private String name;
    private boolean isOut;
    private String bowlerName;
    private int scoreInTheMatch;
    private int wicketTakenInTheMatch;
}

