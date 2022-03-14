package com.cricketgame.exceptions;

import com.cricketgame.models.exceptionmodels.ExceptionTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerAdvisor{

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<Object> handleTeamNotFoundException(TeamNotFoundException teamNotFoundException){
        ExceptionTemplate exceptionTemplate = new
                ExceptionTemplate(teamNotFoundException.getStatus(), teamNotFoundException.getDetails(), teamNotFoundException.getInstant());
        return new ResponseEntity<Object>(exceptionTemplate, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatabaseErrorException.class)
    public ResponseEntity<Object> handleDataBaseErrorException(DatabaseErrorException databaseErrorException){
        ExceptionTemplate exceptionTemplate = new
                ExceptionTemplate(databaseErrorException.getStatus(), databaseErrorException.getDetails(), databaseErrorException.getInstant());
        return new ResponseEntity<Object>(exceptionTemplate, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<Object> handleMatchNotFoundException(MatchNotFoundException matchNotFoundException){
        ExceptionTemplate exceptionTemplate = new
                ExceptionTemplate(matchNotFoundException.getStatus(), matchNotFoundException.getDetails(), matchNotFoundException.getInstant());
        return new ResponseEntity<Object>(exceptionTemplate , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Object> handlePlayerNotFoundException(PlayerNotFoundException playerNotFoundException){
        ExceptionTemplate exceptionTemplate = new
                ExceptionTemplate(playerNotFoundException.getStatus(), playerNotFoundException.getDetails(), playerNotFoundException.getInstant());
        return new ResponseEntity<Object>(exceptionTemplate, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlayerNotFoundInMatchException.class)
    public ResponseEntity<Object> handlePlayerNotFoundException(PlayerNotFoundInMatchException playerNotFoundInMatchException){
        ExceptionTemplate exceptionTemplate = new
                ExceptionTemplate(playerNotFoundInMatchException.getStatus(), playerNotFoundInMatchException.getDetails(), playerNotFoundInMatchException.getInstant());
        return new ResponseEntity<Object>(exceptionTemplate, HttpStatus.NOT_FOUND);
    }


}
