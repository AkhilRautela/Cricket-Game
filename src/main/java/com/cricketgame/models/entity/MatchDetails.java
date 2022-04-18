package com.cricketgame.models.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Entity
@Data
@Table(name = "match_details")
@Document
public class MatchDetails {
    @Id
    private int matchId;
    private int totalOvers;
}
