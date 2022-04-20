package com.cricketgame.repositories;

import com.cricketgame.models.entity.MatchDetails;

public interface MatchDetailRepo {
    MatchDetails save(MatchDetails matchDetails);
}
