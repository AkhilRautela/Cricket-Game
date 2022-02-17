package com.cricketgame.models;

public class Player {
    public String name;
    public int rating;
    public PlayerType playertype;

    Player(String name, int rating) {
        this.name = name;
        this.rating = rating;
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
