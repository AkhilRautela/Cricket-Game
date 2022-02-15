package com.cricketgame.utils;

import com.cricketgame.models.Teams;

import java.util.Random;

public class MatchUtils {

    public static boolean checkValidTeamName(String name) {
        try {
            Teams.valueOf(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getRandomNumber(int low, int high) {
        Random random = new Random();
        return random.ints(low, high).findFirst().getAsInt();
    }

}

