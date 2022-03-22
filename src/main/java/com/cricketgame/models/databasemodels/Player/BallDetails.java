package com.cricketgame.models.databasemodels.Player;

import com.cricketgame.models.enums.BallType;
import lombok.Data;

@Data
public class BallDetails {

    private int scoreInTheBall;
    private BallType ballType;
    private int over;
    private int ballNumber;
}
