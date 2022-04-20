package com.cricketgame.repositories;

import com.cricketgame.models.entity.PlayerDetails;

import java.util.ArrayList;
import java.util.List;

public interface PlayerDetailRepo {
    PlayerDetails findByPlayerId(int playerId);
    List<PlayerDetails> findByTeamId(int teamId);
    PlayerDetails save(PlayerDetails playerDetails);
}
