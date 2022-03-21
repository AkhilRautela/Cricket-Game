package com.cricketgame.exceptions;

import com.cricketgame.models.exceptionmodels.ExceptionTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(CricketGameException.class)
    public ResponseEntity<Object> handleTeamNotFoundException(CricketGameException cricketGameException) {
        ExceptionTemplate exceptionTemplate = new ExceptionTemplate(cricketGameException.getDetails(), cricketGameException.getInstant());
        return new ResponseEntity<Object>(exceptionTemplate, cricketGameException.getStatus());
    }


}
