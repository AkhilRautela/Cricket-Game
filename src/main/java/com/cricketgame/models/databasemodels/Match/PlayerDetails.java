package com.cricketgame.models.databasemodels.Match;

public class PlayerDetails {
    String name;
    boolean isOut;
    String bowlerName;
    int scoreInTheMatch;
    int wicketTakenInTheMatch;

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public String getBowler() {
        return bowlerName;
    }

    public void setBowler(String bowler) {
        this.bowlerName = bowler;
    }

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
}

