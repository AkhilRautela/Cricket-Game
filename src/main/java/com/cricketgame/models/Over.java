package com.cricketgame.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Over {
    private ArrayList<Ball> balls;
    private Player bowler;

    public Over() {
        balls = new ArrayList<Ball>();
    }

}
