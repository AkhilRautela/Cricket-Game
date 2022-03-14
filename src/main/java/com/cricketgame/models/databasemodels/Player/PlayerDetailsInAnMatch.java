package com.cricketgame.models.databasemodels.Player;

import com.cricketgame.models.Ball;
import com.cricketgame.models.Player;

import java.util.ArrayList;

public class PlayerDetailsInAnMatch {

    ArrayList <Player> WicketTaken;
    ArrayList <BallDetails> BallsPlayed;

    public ArrayList<Player> getWicketTaken() {
        return WicketTaken;
    }

    public void setWicketTaken(ArrayList<Player> wicketTaken) {
        WicketTaken = wicketTaken;
    }

    public ArrayList<BallDetails> getBallsPlayed() {
        return BallsPlayed;
    }

    public void setBallsPlayed(ArrayList<BallDetails> ballsPlayed) {
        BallsPlayed = ballsPlayed;
    }

}
