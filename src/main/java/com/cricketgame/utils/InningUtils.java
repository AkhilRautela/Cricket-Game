package com.cricketgame.utils;

import com.cricketgame.constants.Constants;
import com.cricketgame.models.Inning;
import com.cricketgame.models.entity.PlayerDetails;
import com.cricketgame.models.enums.BallType;
import com.cricketgame.models.enums.PlayerType;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class InningUtils {
    /**
     * get next batsman other then player that is out or not a nonStriker
     *
     * @param strikerIndex
     * @param nonStrikerIndex
     * @param inning
     * @return
     */
    public static int getNextBatsman(int strikerIndex, int nonStrikerIndex, Inning inning) {
        for (int i = 0; i < 11; i++) {
            if (inning.getIsPlayerOut().get(i) == false && i != nonStrikerIndex) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Select next bowler for the over.
     *
     * @param players
     * @return
     */
    public static int getBowlerIndexForTheOver(ArrayList<PlayerDetails> players) {
        ArrayList<Integer> bowlers = new ArrayList<Integer>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerType().equals("BOWLER")) {
                bowlers.add(i);
            }
        }
        return bowlers.get(GeneralUtils.getRandomNumber(0, bowlers.size() - 1));
    }


    /**
     * Check wether the opposite team has played , if played then score greater than that team or not.
     *
     * @param inning
     * @return
     */
    public static boolean isScoreMoreThenOppositeTeam(Inning inning) {
        return inning.isFirstInningDone() && inning.getOppositeTeamScore() < inning.getScore();
    }


    /**
     * Check wether all players in the inning are out or not.
     *
     * @param inning
     * @return
     */
    public static boolean isAllOut(Inning inning) {
        return inning.getIsPlayerOut().stream().filter((x) -> x== true).count() == Constants.TOTAL_WICKETS;
    }




}
