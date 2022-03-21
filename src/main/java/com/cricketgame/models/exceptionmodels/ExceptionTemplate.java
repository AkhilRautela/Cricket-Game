package com.cricketgame.models.exceptionmodels;

import java.time.Instant;

public class ExceptionTemplate {

    String message;
    Instant timeStamp;

    public ExceptionTemplate(String message, Instant instant) {
        this.message = message;
        this.timeStamp = instant;
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
