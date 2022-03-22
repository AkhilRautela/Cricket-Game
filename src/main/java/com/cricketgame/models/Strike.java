package com.cricketgame.models;

import lombok.Data;

@Data
public class Strike {
    private int strikerIndex;
    private int nonStrikerIndex;
    private int playerFactor;
    private int currentBowlerIndex;
}
