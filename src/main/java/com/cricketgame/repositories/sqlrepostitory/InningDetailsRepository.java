package com.cricketgame.repositories.sqlrepostitory;

import com.cricketgame.models.entity.InningDetails;
import com.cricketgame.repositories.InningDetailRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sqlInningRepo")
public interface InningDetailsRepository extends JpaRepository<InningDetails, Integer>, InningDetailRepo {
    List<InningDetails> findByMatchId(int id);
}
