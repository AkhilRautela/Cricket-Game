package com.cricketgame.exceptions;

import java.time.Instant;

public class PlayerNotFoundException extends RuntimeException{

    String details = "";
    int status;
    Instant instant;

    public PlayerNotFoundException(String details , int status){
        this.details = details;
        this.status = status;
        this.instant = Instant.now();
    }

    public PlayerNotFoundException(){
        this.instant = Instant.now();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

}
