package com.cricketgame.repositories;

import com.cricketgame.models.entity.InningDetails;

import java.util.List;

public interface InningDetailRepo {
    InningDetails save(InningDetails inningDetails);

    List<InningDetails> findByMatchId(int matchId);
}
