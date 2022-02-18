package com.cricketgame.models;

public class Player {
    public String name;
    public int rating;
    public PlayerType playertype;

    Player(String name, int rating , PlayerType playertype) {
        this.name = name;
        this.rating = rating;
        this.playertype = playertype;
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
