package com.cricketgame.service;

import com.cricketgame.constants.Constants;
import com.cricketgame.models.*;
import com.cricketgame.models.entity.BallDetails;
import com.cricketgame.models.entity.InningDetails;
import com.cricketgame.models.entity.OverDetails;
import com.cricketgame.models.entity.PlayerDetails;
import com.cricketgame.models.enums.BallType;
import com.cricketgame.models.enums.PlayerType;
import com.cricketgame.repositories.BallDetailRepo;
import com.cricketgame.repositories.InningDetailRepo;
import com.cricketgame.repositories.OverDetailRepo;
import com.cricketgame.utils.GeneralUtils;
import com.cricketgame.utils.InningUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class InningServiceImpl {

    @Autowired
    TeamServiceImpl teamService;
    @Autowired
    IdGenerator idGenerator;

    @Autowired
    BallDetailRepo ballDetailsRepository;
    @Autowired
    InningDetailRepo inningDetailsRepostiory;
    @Autowired
    OverDetailRepo overDetailsRepository;

    public void createAndStartInnings(int matchId, int battingTeamId, int bowlingTeamId, int overs){

        InningDetails inningDetails;
        InningDetails inningDetailsResponse;


        inningDetails = new InningDetails(idGenerator.getNextInningId() ,battingTeamId, bowlingTeamId, matchId);

        inningDetailsResponse = inningDetailsRepostiory.save(inningDetails);
        int inning1Id = inningDetailsResponse.getInningId();

        inningDetails = new InningDetails(idGenerator.getNextInningId(), battingTeamId, bowlingTeamId, matchId);
        inningDetailsResponse= inningDetailsRepostiory.save(inningDetails);
        int inning2Id = inningDetailsResponse.getInningId();

        Team battingTeam = teamService.createTeam(battingTeamId);
        Team bowlingTeam = teamService.createTeam(bowlingTeamId);



        Inning inning1 = new Inning(battingTeam, bowlingTeam, overs, false, 0);
        startInning(inning1, inning1Id);

        Inning inning2 = new Inning(bowlingTeam, battingTeam, overs, true, inning1.getScore());
        startInning(inning2, inning2Id);
    }

    /**
     * start the inning
     * @param inning
     * @param inningId
     * @throws IOException
     */
    public void startInning(Inning inning, int inningId)  {

        Strike strike = new Strike();
        strike.setStrikerIndex(0);
        strike.setNonStrikerIndex(1);

        for (int i = 1; i <= inning.getTotalOvers(); i++) {
            strike.setCurrentBowlerIndex(InningUtils.getBowlerIndexForTheOver(inning.getBowlingTeam().getPlayerDetails()));
            if (InningUtils.isScoreMoreThenOppositeTeam(inning) || InningUtils.isAllOut(inning)) return;
            OverDetails overDetails = new OverDetails();
            overDetails.setOverId(idGenerator.getNextOverId());
            overDetails.setInningId(inningId);
            overDetails.setBowlerId(inning.getBowlingTeam().getPlayerDetails().get(strike.getCurrentBowlerIndex()).getPlayerId());
            OverDetails overDetailsResponse = overDetailsRepository.save(overDetails);
            startOver(inning, strike, overDetailsResponse);
        }

    }

    /**
     * Start the over corresponding to the inning provided
     *
     * @param inning
     * @param strike
     * @param overDetails
     */
    public void startOver(Inning inning, Strike strike, OverDetails overDetails)  {

        strike.setPlayerFactor(updatePlayerFactor(inning, strike));

        for (int j = 1; j <= Constants.TOTAL_BALLS_IN_ONE_OVER; j++) {

            if (InningUtils.isScoreMoreThenOppositeTeam(inning) || InningUtils.isAllOut(inning)) return;

            BallDetails ballDetails = new BallDetails();
            ballDetails.setBallId(idGenerator.getNextBallId());
            ballDetails.setOverId(overDetails.getOverId());
            ballDetails.setInningId(overDetails.getInningId());
            ballDetails.setStrikerId(inning.getBattingTeam().getPlayerDetails().get(strike.getStrikerIndex()).getPlayerId());
            ballDetails.setNonStrikerId(inning.getBattingTeam().getPlayerDetails().get(strike.getNonStrikerIndex()).getPlayerId());

            int scoreInTheBall = GeneralUtils.getRandomNumber(0, strike.getPlayerFactor());

            if (scoreInTheBall == 5 || scoreInTheBall == 0) { // counted as 0 runs
                ballDetails.setBallType(BallType.NOTARUN.toString());
                ballDetailsRepository.save(ballDetails);
                continue;
            }

            if (scoreInTheBall > Constants.MAX_RUNS_IN_ONE_BALL) {
                ballDetails.setBallType(BallType.WICKET.toString());
                ballDetailsRepository.save(ballDetails);
                inning.getIsPlayerOut().set(strike.getStrikerIndex(), true);
                strike.setStrikerIndex(InningUtils.getNextBatsman(strike.getStrikerIndex(), strike.getNonStrikerIndex(), inning));
                continue;
            }

            ballDetails.setBallType(BallType.RUN.toString());
            ballDetails.setRuns(scoreInTheBall);
            ballDetailsRepository.save(ballDetails);
            handleRuns(scoreInTheBall, inning, strike);
        }

    }

    /**
     * Handle the run scored in an ball
     *
     * @param scoreInTheBall
     * @param inning
     * @param strike
     */
    public void handleRuns(int scoreInTheBall, Inning inning, Strike strike) {
        inning.setScore(inning.getScore() + scoreInTheBall);
        if (scoreInTheBall % 2 == 1) {
            int temp = strike.getStrikerIndex();
            strike.setStrikerIndex(strike.getNonStrikerIndex());
            strike.setNonStrikerIndex(temp);
            strike.setPlayerFactor(updatePlayerFactor(inning, strike));
        }

    }

    /**
     * Update player factor for the new batsman and bowler
     *
     * @param inning
     * @param strike
     * @return
     */
    private int updatePlayerFactor(Inning inning, Strike strike) {
        PlayerDetails bowler = inning.getBowlingTeam().getPlayerDetails().get(strike.getCurrentBowlerIndex());
        PlayerDetails striker = inning.getBattingTeam().getPlayerDetails().get(strike.getStrikerIndex());

        if (striker.getPlayerType() == PlayerType.BATSMAN.toString()) {
            if (striker.getRating() >= 8 && bowler.getRating() >= 8) {
                return 9;
            }
            if (striker.getRating() >= 8) return 8;
            else return 10;
        }

        if (bowler.getRating() < 8) return 9;
        return 10;
    }


}
