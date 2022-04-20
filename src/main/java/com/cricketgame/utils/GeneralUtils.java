package com.cricketgame.utils;

import java.util.Random;

public class GeneralUtils {

    /**
     * Generate Random number between low and high.
     *
     * @param low
     * @param high
     * @return
     */
    public static int getRandomNumber(int low, int high) {
        Random random = new Random();
        return random.ints(low, high).findFirst().getAsInt();
    }

}
