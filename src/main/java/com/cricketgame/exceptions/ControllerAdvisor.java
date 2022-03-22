package com.cricketgame.exceptions;

import com.cricketgame.models.exceptionmodels.ExceptionTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;


@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(CricketGameException.class)
    public ResponseEntity<Object> handleTeamNotFoundException(CricketGameException cricketGameException) {
        ExceptionTemplate exceptionTemplate = new ExceptionTemplate(cricketGameException.getDetails(), cricketGameException.getInstant());
        return new ResponseEntity<Object>(exceptionTemplate, cricketGameException.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleDefaultException(Exception exception) {
        ExceptionTemplate exceptionTemplate = new ExceptionTemplate("Internal Error", Instant.now());
        return new ResponseEntity<Object>(exceptionTemplate, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
