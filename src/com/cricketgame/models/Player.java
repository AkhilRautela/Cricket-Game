package com.cricketgame.models;

public class Player {
    public String name;
    public int bowlingRating;
    public int battingRating;
    int fieldingRating;
    public int score;
    public int wicket;
    public boolean isOut;

    Player(String name, int bowlingRating, int battingRating, int fieldingRating) {
        this.name = name;
        this.battingRating = battingRating;
        this.bowlingRating = bowlingRating;
        this.fieldingRating = fieldingRating;
        this.score = 0;
        this.wicket = 0;
        this.isOut = false;
    }

    void getRating() {
        // Getting rating from database
    }

    void updateRating() {
        // Update Rating according to this game
    }

    void pushRating() {
        // Push Rating to database according to this game
    }
}
