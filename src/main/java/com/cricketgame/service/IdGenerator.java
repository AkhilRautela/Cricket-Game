package com.cricketgame.service;

import com.cricketgame.exceptions.CricketGameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;
import java.util.Properties;

@Service
public class IdGenerator {
    
    Properties getProperties(){
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("id_details");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new CricketGameException("Internal File Error" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  properties;
    }

    void setProperties(Properties properties){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream("id_details");
            properties.store(fileOutputStream,null);
            fileOutputStream.close();
        } catch (IOException e) {
            throw  new CricketGameException("Internal File Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    synchronized int getNextMatchId() {
        Properties properties = getProperties();
        int matchId = Integer.parseInt(properties.getProperty("matchId"));
        System.out.println(matchId);
        properties.setProperty("matchId", String.valueOf(matchId + 1));
        setProperties(properties);
        return matchId;
    }

    synchronized int getNextInningId()  {
        Properties properties = getProperties();
        int inningId = Integer.parseInt(properties.getProperty("inningId"));
        properties.setProperty("inningId", String.valueOf(inningId + 1));
        setProperties(properties);
        return inningId;
    }

    synchronized int getNextOverId(){
        Properties properties = getProperties();
        int overId = Integer.parseInt(properties.getProperty("overId"));
        properties.setProperty("OverId", String.valueOf(overId + 1));
        setProperties(properties);
        return overId;
    }

    synchronized int getNextBallId() {
        Properties properties = getProperties();
        int ballId = Integer.parseInt(properties.getProperty("ballId"));
        properties.setProperty("ballId", String.valueOf(ballId + 1));
        setProperties(properties);
        return ballId;
    }

}
