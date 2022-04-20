package com.cricketgame.models.response;

import com.cricketgame.models.entity.BallDetails;
import lombok.Data;

import java.util.ArrayList;

@Data
public class OverInfo {
    private int overId;
    private ArrayList <BallDetails> ballDetails;
}
