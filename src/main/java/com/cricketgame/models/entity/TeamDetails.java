package com.cricketgame.models.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@Entity
@Table(name = "team_details")
@Document
public class TeamDetails {
    @Id
    private int teamId;
    private String name;
}
