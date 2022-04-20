package com.cricketgame.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class CricketGameException extends RuntimeException {

    HttpStatus status;
    String details;
    Instant instant;

    public CricketGameException(String details, HttpStatus status) {
        this.status = status;
        this.details = details;
        instant = Instant.now();
    }

}
