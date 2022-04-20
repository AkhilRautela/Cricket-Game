package com.cricketgame.repositories.sqlrepostitory;

import com.cricketgame.models.entity.TeamDetails;
import com.cricketgame.repositories.TeamDetailRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("sqlTeamRepo")
public interface TeamDetailsRepository extends JpaRepository<TeamDetails, Integer>, TeamDetailRepo {
    TeamDetails findByName(String name);
    TeamDetails findByTeamId(int teamId);
}
