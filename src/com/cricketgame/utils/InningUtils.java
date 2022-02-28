package com.cricketgame.utils;

import com.cricketgame.Constants;
import com.cricketgame.models.*;
import com.cricketgame.models.enums.BallType;
import com.cricketgame.models.enums.PlayerType;

import java.util.ArrayList;

public class InningUtils {
    public static int getNextBatsman(int strikerIndex, int nonStrikerIndex , Inning inning){
        for(int i = 0; i < 11; i++){
            if(!isOut(inning, inning.getBattingTeam().getPlayers().get(i)) && i != nonStrikerIndex){
                return i;
            }
        }
        return 0;
    }

    public static boolean isOut(Inning inning, Player player){
        for (int i = 0; i < inning.getOvers().size(); i++) {
            Over over = inning.getOvers().get(i);
            for (int j = 0; j < over.getBalls().size(); j++) {
                Ball b = over.getBalls().get(j);
                if(b.getBallType() == BallType.WICKET && b.getStriker() == player){
                    return true;
                }
            }
        }
        return false;
    }

    public static int getBowlerForTheOver(ArrayList<Player> players) {
        ArrayList <Integer> bowlers = new ArrayList<Integer>();
        for(int i = 0; i < 11; i++){
            if(players.get(i).getPlayertype() == PlayerType.BOWLER){
                bowlers.add(i);
            }
        }
        return bowlers.get(MatchUtils.getRandomNumber(0,bowlers.size()-1));
    }

    // check wether the opposite team has played , if played then score greater than that team
    public static boolean checkScoreMoreThenOppositeTeam(Inning inning) {
        return inning.isFirstInningDone() && inning.getOppositeTeamScore() < MatchUtils.getScore(inning);
    }

    public static boolean checkAllOut(Inning inning) {
        if (MatchUtils.getTotalWickets(inning) == Constants.TOTAL_WICKETS) {
            return true;
        }
        return false;
    }

    // swap striker and non-striker for odd runs
    public static int[] swapPlayer(int strikerIndex, int nonStrikerIndex) {
        int temp = strikerIndex;
        strikerIndex = nonStrikerIndex;
        nonStrikerIndex = temp;
        return new int[]{strikerIndex, nonStrikerIndex};
    }

}
