package com.cricketgame.repositories;

import com.cricketgame.models.entity.OverDetails;

import java.util.List;

public interface OverDetailRepo {
    OverDetails save(OverDetails overDetails);
    List<OverDetails> findOversDoneByThePlayer(int playerId, int inningId, int inningId1);
}
