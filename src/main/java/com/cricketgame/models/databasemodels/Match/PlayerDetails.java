package com.cricketgame.models.databasemodels.Match;

import java.util.ArrayList;

public class PlayerDetails{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScoreInTheMatch() {
        return scoreInTheMatch;
    }

    public void setScoreInTheMatch(int scoreInTheMatch) {
        this.scoreInTheMatch = scoreInTheMatch;
    }

    public int getWicketTakenInTheMatch() {
        return wicketTakenInTheMatch;
    }

    public void setWicketTakenInTheMatch(int wicketTakenInTheMatch) {
        this.wicketTakenInTheMatch = wicketTakenInTheMatch;
    }

    int scoreInTheMatch;
    int wicketTakenInTheMatch;
}

