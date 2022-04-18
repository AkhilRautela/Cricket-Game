package com.cricketgame.repositories;


import com.cricketgame.models.entity.BallDetails;

import java.util.ArrayList;
import java.util.List;

public interface BallDetailRepo {

    BallDetails save(BallDetails ballDetails);

    ArrayList<BallDetails> getBallsForTheOver(int overId);

    ArrayList<BallDetails> getBallsPlayed(int playerId, int inningId, int inningId1);
}
