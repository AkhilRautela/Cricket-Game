package com.cricketgame.models.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ball_details")
@Document
public class BallDetails {
    @Id
    private int ballId;
    private int strikerId;
    private int inningId;
    private int runs;
    private String ballType;
    private int overId;
    private int nonStrikerId;
}
