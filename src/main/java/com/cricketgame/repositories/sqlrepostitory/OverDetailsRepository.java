package com.cricketgame.repositories.sqlrepostitory;

import com.cricketgame.models.entity.OverDetails;
import com.cricketgame.repositories.OverDetailRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository("sqlOverRepo")
public interface OverDetailsRepository extends JpaRepository<OverDetails, Integer>, OverDetailRepo {
    @Query(value = "SELECT * FROM over_details WHERE bowler_id = ?1 AND inning_id in (?2, ?3)", nativeQuery = true)
    List<OverDetails> findOversDoneByThePlayer(int bowlerId, int inning1Id, int inning2id);
}
