package org.predict.service;

import lombok.extern.log4j.Log4j2;
import org.data.model.predict.entity.GameAttempt;
import org.data.model.predict.entity.PredictGame;
import org.data.model.predict.enums.GameStatus;
import org.data.model.predict.enums.InputNumberLevel;
import org.data.model.request.PredictGameRequest;
import org.data.model.response.PredictGameResponse;
import org.predict.dao.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

@Service
@Log4j2
public class GameService {

    @Autowired
    PredictService predictService;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private Map<String, PredictGame> gameMap;

    public PredictGameResponse newGame(PredictGameRequest predictGameRequest) {
        return startGame(createGame(predictGameRequest));
    }

    public PredictGame createGame(PredictGameRequest predictGameRequest) { //

        PredictGame predictGame = predictService.createGame(predictGameRequest);
        log.info("Game Created : " + predictGame);
        //gameMap.put(predictGame.getGameId(), predictGame);
        gameDao.cacheSave(predictGame);
        return predictGame;
    }

    public PredictGameResponse startGame(PredictGame predictGame) { //
        return predictGameResponse(predictGame);
    }

    public PredictGameResponse attempt(PredictGameRequest predictGameRequest) throws Exception {
        PredictGame predictGame = getGame(predictGameRequest.getGameId());
        verifyGame(predictGame);
        //log.info("Before Attempt : " + predictGame);
        InputNumberLevel inputNumberLevel = verifyNumberLevel(predictGame.getGeneratedRandomNumber(), predictGameRequest.getPredictedValue());
        GameAttempt gameAttempt = createGameAttempt(predictGame, predictGameRequest, inputNumberLevel);
        updateGameStatus(predictGame, gameAttempt);
        //log.info("After Attempt : " + predictGame);
        saveGameData(predictGame);
        PredictGameResponse predictGameResponse = predictGameResponse(predictGame, inputNumberLevel);
        return predictGameResponse;
    }

    private PredictGame getGame(UUID gameId) throws Exception {
        return gameDao.getGameById(gameId);
    }

    private void updateGameStatus(PredictGame predictGame, GameAttempt gameAttempt) throws Exception {
        if(predictGame.getAttemptsRemaining() <= 0)
            throw new Exception("Game attempts are 0, Game has ended for id " + predictGame.getGameId());
        predictGame.reduceAttempts();
        predictGame.setGameStatus(gameStatus(predictGame, gameAttempt));
        predictGame.getGameAttempts().add(gameAttempt);
    }

    private GameStatus gameStatus(PredictGame predictGame, GameAttempt gameAttempt) {
        long lastGameAttemptDifference = gameAttempt.getCreatedAt().until( LocalDateTime.now(), ChronoUnit.SECONDS );
        if(lastGameAttemptDifference > 300)
            return GameStatus.TIME_EXPIRED;

        boolean noGameAttemptsRemaining = predictGame.getAttemptsRemaining() == 0;
        boolean equalStatus = gameAttempt.getInputNumberLevel().equals(InputNumberLevel.EQUAL);
        if(noGameAttemptsRemaining || equalStatus)
            return GameStatus.GAME_OVER;

        return GameStatus.IN_PROGRESS;
    }

    private InputNumberLevel verifyNumberLevel(int randomNumber, int predictValue) {
        if(predictValue < randomNumber) {
            return InputNumberLevel.LOWER;
        } else if(predictValue > randomNumber) {
            return InputNumberLevel.HIGHER;
        } else return InputNumberLevel.EQUAL;
    }

    private GameAttempt createGameAttempt(PredictGame predictGame, PredictGameRequest predictGameRequest, InputNumberLevel inputNumberLevel) {
        return GameAttempt.builder()
                .attemptId(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .gameId(predictGame.getGameId())
                .inputNumberLevel(inputNumberLevel)
                //.predictGame(predictGame)
                .inputValue(predictGameRequest.getPredictedValue())
                .build();
    }

    public void saveGameData(PredictGame predictGame) {
        /*if(predictGame.getGameStatus().equals(GameStatus.GAME_OVER))
            gameMap.remove(gameId);
        else*/
        gameDao.cacheSave(predictGame);
    }


    public void verifyGame(PredictGame predictGame) throws Exception {
        if(predictGame == null)
            throw new Exception("Game not found for game id " + predictGame.getGameId());
        if(predictGame.getGameStatus().equals(GameStatus.GAME_OVER) || predictGame.getAttemptsRemaining() == 0)
            throw new Exception("Game over " + predictGame.getGameId());
    }


    private PredictGameResponse predictGameResponse(PredictGame predictGame) {
        return predictGameResponse(predictGame, null);
    }

    private PredictGameResponse predictGameResponse(PredictGame predictGame, InputNumberLevel inputNumberLevel) {
        return PredictGameResponse.builder()
                .gameId(predictGame.getGameId())
                .userId(predictGame.getUserId())
                .bound(predictGame.getBound() - 1)
                .origin(predictGame.getOrigin())
                .attemptsAllowed(predictGame.getAttemptsAllowed())
                .attemptsRemaining(predictGame.getAttemptsRemaining())
                .gameStatus(predictGame.getGameStatus())
                .inputNumberLevel(inputNumberLevel)
                .build();
    }
}
