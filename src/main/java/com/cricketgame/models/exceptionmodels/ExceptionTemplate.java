package com.cricketgame.models.exceptionmodels;

import java.time.Instant;

public class ExceptionTemplate {

    int status;
    String message;
    Instant timeStamp;

    public ExceptionTemplate(int status, String message, Instant instant){
        this.status = status;
        this.message = message;
        this.timeStamp = instant;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }



}
