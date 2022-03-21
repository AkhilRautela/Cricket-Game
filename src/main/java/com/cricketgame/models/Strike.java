package com.cricketgame.models;

public class Strike {
    int strikerIndex;
    int nonStrikerIndex;
    int playerFactor;
    int currentBowlerIndex;

    public int getCurrentBowlerIndex() {
        return currentBowlerIndex;
    }

    public void setCurrentBowlerIndex(int currentBowlerIndex) {
        this.currentBowlerIndex = currentBowlerIndex;
    }

    public int getStrikerIndex() {
        return strikerIndex;
    }

    public void setStrikerIndex(int strikerIndex) {
        this.strikerIndex = strikerIndex;
    }

    public int getNonStrikerIndex() {
        return nonStrikerIndex;
    }

    public void setNonStrikerIndex(int nonStrikerIndex) {
        this.nonStrikerIndex = nonStrikerIndex;
    }

    public int getPlayerFactor() {
        return playerFactor;
    }

    public void setPlayerFactor(int playerFactor) {
        this.playerFactor = playerFactor;
    }
}
