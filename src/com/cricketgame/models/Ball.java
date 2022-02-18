package com.cricketgame.models;

public class Ball {

    private BallType ballType;
    private int runsOnTheBall;
    private Player striker;

    public Ball(){
        this.runsOnTheBall = 0;
    }

    public BallType getBallType() {
        return ballType;
    }

    public void setBallType(BallType ballType) {
        this.ballType = ballType;
    }

    public int getRunsOnTheBall() {
        return runsOnTheBall;
    }

    public void setRunsOnTheBall(int runsOnTheBall) {
        this.runsOnTheBall = runsOnTheBall;
    }

    public Player getStriker() {
        return striker;
    }

    public void setStriker(Player striker) {
        this.striker = striker;
    }

}
