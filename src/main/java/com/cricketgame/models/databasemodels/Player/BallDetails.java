package com.cricketgame.models.databasemodels.Player;

import com.cricketgame.models.enums.BallType;

public class BallDetails {

    int scoreInTheBall;
    BallType ballType;
    int over;
    int ballNumber;

    public int getOver() {
        return over;
    }

    public void setOver(int over) {
        this.over = over;
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber(int ballNumber) {
        this.ballNumber = ballNumber;
    }

    public int getScoreInTheBall() {
        return scoreInTheBall;
    }

    public void setScoreInTheBall(int scoreInTheBall) {
        this.scoreInTheBall = scoreInTheBall;
    }

    public BallType getBallType() {
        return ballType;
    }

    public void setBallType(BallType ballType) {
        this.ballType = ballType;
    }


}
