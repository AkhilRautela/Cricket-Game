package com.cricketgame.exceptions;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class CricketGameException extends RuntimeException {

    HttpStatus status;
    String details;
    Instant instant;

    public CricketGameException(String details, HttpStatus status) {
        this.status = status;
        this.details = details;
        instant = Instant.now();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }
}
