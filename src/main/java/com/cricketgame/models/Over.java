package com.cricketgame.models;

import java.util.ArrayList;

public class Over {
    private ArrayList<Ball> balls;
    private Player bowler;

    public Over() {
        balls = new ArrayList<Ball>();
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    public Player getBowler() {
        return bowler;
    }

    public void setBowler(Player bowler) {
        this.bowler = bowler;
    }


}
