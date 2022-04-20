package com.cricketgame.repositories;

import com.cricketgame.models.entity.TeamDetails;

public interface TeamDetailRepo {
    TeamDetails findByName(String name);
    TeamDetails findByTeamId(int teamId);
    TeamDetails save(TeamDetails teamDetails);
}
