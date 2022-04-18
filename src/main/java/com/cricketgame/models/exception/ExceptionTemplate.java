package com.cricketgame.models.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ExceptionTemplate {
    private String message;
    private Instant timeStamp;
}
