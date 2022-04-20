package com.cricketgame.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Table(name = "inning_details")
@Entity
@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class InningDetails {
    @Id
    private int inningId;
    private int battingTeamId;
    private int bowlingTeamId;
    private int matchId;
}
