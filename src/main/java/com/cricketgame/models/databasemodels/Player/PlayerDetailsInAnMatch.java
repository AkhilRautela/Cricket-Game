package com.cricketgame.models.databasemodels.Player;

import com.cricketgame.models.Player;
import lombok.Data;

import java.util.ArrayList;

@Data
public class PlayerDetailsInAnMatch {

    private ArrayList<Player> WicketTaken;
    private ArrayList<BallDetails> BallsPlayed;


}
