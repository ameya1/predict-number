package org.predict.service;

import lombok.extern.log4j.Log4j2;

import org.data.dao.RedisDao;
import org.data.model.predict.entity.GameAttempt;
import org.data.model.predict.entity.PredictGame;
import org.data.model.predict.enums.GameStatus;
import org.data.model.predict.enums.InputNumberLevel;
import org.data.model.request.PredictGameRequest;
import org.data.model.response.PredictGameResponse;
import org.data.model.user.entity.User;
import org.hibernate.Session;
import org.predict.dao.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
