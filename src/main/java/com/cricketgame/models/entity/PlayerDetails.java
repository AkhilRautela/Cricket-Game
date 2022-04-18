package com.cricketgame.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@Entity
@Table(name = "player_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Document
public class PlayerDetails {
    @Id
    private int playerId;
    private String name;
    private int rating;
    private String playerType;
    private int teamId;
}
