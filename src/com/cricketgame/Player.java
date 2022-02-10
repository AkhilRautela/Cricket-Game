package com.cricketgame;

public class Player {
    String name;
    int bowlingRating;
    int battingRating;
    int fieldingRating;
    int score;
    int wicket;

    Player(String name, int bowlingRating, int battingRating, int fieldingRating) {
        this.name = name;
        this.battingRating = battingRating;
        this.bowlingRating = bowlingRating;
        this.fieldingRating = fieldingRating;
        this.score = 0;
        this.wicket = 0;
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
