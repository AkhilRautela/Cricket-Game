package com.cricketgame.models;

import com.cricketgame.models.enums.BallType;
import lombok.Data;

@Data
public class Ball {

    private BallType ballType;
    private int runsOnTheBall;
    private Player striker;

    public Ball() {
        this.runsOnTheBall = 0;
    }

}
