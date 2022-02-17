package com.cricketgame.utils;

import com.cricketgame.models.Player;
import com.cricketgame.models.Team;

import java.util.ArrayList;

public class InningUtils {
    public static int getNextBatsman(int strikerIndex, int nonStrikerIndex , ArrayList<Boolean> isOut){
        for(int i = 0; i < 11; i++){
            if(isOut.get(i) == false && nonStrikerIndex != i){
                return i;
            }
        }
        return 0;
    }
}
