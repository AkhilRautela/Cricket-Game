package com.cricketgame.models.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Entity
@Data
@Table(name = "over_details")
@Document
public class OverDetails {
    @Id
    private int overId;
    private int bowlerId;
    private int inningId;
}
