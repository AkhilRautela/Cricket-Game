package com.cricketgame.utils;

import com.cricketgame.models.Player;
import com.cricketgame.models.PlayerType;
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

    public static int getBowlerForTheOver(ArrayList<Player> players) {
        ArrayList <Integer> bowlers = new ArrayList<Integer>();
        for(int i = 0; i < 11; i++){
            if(players.get(i).playertype == PlayerType.BOWLER){
                bowlers.add(i);
            }
        }
        return bowlers.get(MatchUtils.getRandomNumber(0,bowlers.size()-1));
    }
}
