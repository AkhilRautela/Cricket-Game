package com.cricketgame.service;

import com.cricketgame.exceptions.CricketGameException;
import com.cricketgame.models.entity.BallDetails;
import com.cricketgame.models.entity.InningDetails;
import com.cricketgame.models.entity.OverDetails;
import com.cricketgame.models.entity.PlayerDetails;
import com.cricketgame.models.response.OverInfo;
import com.cricketgame.models.response.PlayerDetailsInMatch;
import com.cricketgame.repositories.BallDetailRepo;
import com.cricketgame.repositories.InningDetailRepo;
import com.cricketgame.repositories.OverDetailRepo;
import com.cricketgame.repositories.PlayerDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PlayerServiceImpl {

    @Autowired
    PlayerDetailRepo playerDetailsRepository;
    @Autowired
    InningDetailRepo inningDetailsRepostiory;
    @Autowired
    BallDetailRepo ballDetailsRepository;
    @Autowired
    OverDetailRepo overDetailsRepository;

    public boolean isPresent(int playerId, int teamId){
        Optional<PlayerDetails> playerDetails = Optional.ofNullable(playerDetailsRepository.findByPlayerId(playerId));
        if(playerDetails.isEmpty()){
            throw new CricketGameException("Player with given id is not present", HttpStatus.NOT_FOUND);
        }
        PlayerDetails playerDetail = playerDetails.get();
        return playerDetail.getTeamId() == teamId;
    }

    public int getScore(ArrayList <BallDetails> ballPlayed){
        return ballPlayed.stream().map((ballDetail) -> ballDetail.getRuns()).reduce(0 , (currentRun, runsInTheBall) -> currentRun + runsInTheBall);
    }

    public int getWicketTaken(ArrayList<OverInfo> overInfos) {
        return overInfos.stream().map(OverInfo -> (int)OverInfo.getBallDetails().stream().filter(ballDetails -> ballDetails.getBallType().equals("WICKET")).count())
                .reduce(0, (currentWickets, wicketsInTheOver) -> currentWickets + wicketsInTheOver);
    }

    /**
     * Get a player details by using player's ID.
     * @param playerId
     * @return
     */
    public ResponseEntity<Object> getPlayerDetails(int playerId) {
        try {
            PlayerDetails playerDetails = playerDetailsRepository.findByPlayerId(playerId);
            return new ResponseEntity<Object>(playerDetails, HttpStatus.OK);
        } catch (NullPointerException exception) {
            throw new CricketGameException("Player With the given Id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    public ArrayList<OverInfo> getOversDelivered(int playerId, ArrayList<OverDetails> overDetails) {
        ArrayList <OverInfo> overInfos = new ArrayList<>();
        for(OverDetails overDetail : overDetails){
            OverInfo overInfo = new OverInfo();
            int overId = overDetail.getOverId();
            overInfo.setOverId(overId);
            overInfo.setBallDetails(ballDetailsRepository.getBallsForTheOver(overId));
        }
        return overInfos;
    }

    public PlayerDetailsInMatch getPlayerDetailsInMatch(int matchId, int playerId){
        ArrayList<InningDetails> inningDetails = (ArrayList<InningDetails>) inningDetailsRepostiory.findByMatchId(matchId);
        if(inningDetails.size() == 0){
            throw new CricketGameException("Match With Given Id does not exist", HttpStatus.NOT_FOUND);
        }
        if( !isPresent(playerId, inningDetails.get(0).getBattingTeamId()) && !isPresent(playerId, inningDetails.get(0).getBowlingTeamId()) ){
            throw new CricketGameException("Player not present in the given match Id", HttpStatus.NOT_FOUND);
        }
        ArrayList <OverDetails> overDetails = (ArrayList<OverDetails>) overDetailsRepository.findOversDoneByThePlayer(playerId, inningDetails.get(0).getInningId(), inningDetails.get(1).getInningId());
        ArrayList <OverInfo> oversDelivered = getOversDelivered(playerId, overDetails);
        ArrayList <BallDetails> ballPlayed = ballDetailsRepository.getBallsPlayed(playerId, inningDetails.get(0).getInningId(), inningDetails.get(1).getInningId());
        PlayerDetailsInMatch playerDetailsInMatch = new PlayerDetailsInMatch();
        playerDetailsInMatch.setMatchId(matchId);
        playerDetailsInMatch.setPlayerId(playerId);
        playerDetailsInMatch.setScoreInTheMatch(getScore(ballPlayed));
        playerDetailsInMatch.setWicketTaken(getWicketTaken(oversDelivered));
        playerDetailsInMatch.setBallPlayed(ballPlayed);
        playerDetailsInMatch.setOverDelivered(oversDelivered);
        return  playerDetailsInMatch;
    }
    public ResponseEntity<Object> getPlayerStatsInMatch(int matchId, int playerId) {
        return new ResponseEntity<Object>(getPlayerDetailsInMatch(matchId, playerId), HttpStatus.OK);
    }




}
