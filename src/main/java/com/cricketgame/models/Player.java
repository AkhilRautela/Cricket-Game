package com.cricketgame.models;

import com.cricketgame.models.enums.PlayerType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Player {
    private String name;
    private int rating;
    private PlayerType playertype;

}
