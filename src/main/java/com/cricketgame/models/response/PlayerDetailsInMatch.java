package com.cricketgame.models.response;

import com.cricketgame.models.entity.BallDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlayerDetailsInMatch {
    private int matchId;
    private int playerId;
    private int scoreInTheMatch;
    private int wicketTaken;
    private ArrayList <BallDetails> ballPlayed;
    private ArrayList <OverInfo> overDelivered;
}
