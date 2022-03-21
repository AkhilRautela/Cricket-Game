package com.cricketgame.models;

import com.cricketgame.models.enums.PlayerType;

public class Player {
    private String name;
    private int rating;
    private PlayerType playertype;

    public Player(String name, int rating, PlayerType playertype) {
        this.name = name;
        this.rating = rating;
        this.playertype = playertype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public PlayerType getPlayertype() {
        return playertype;
    }

    public void setPlayertype(PlayerType playertype) {
        this.playertype = playertype;
    }


}
