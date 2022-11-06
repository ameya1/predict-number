package org.predict.service;

import lombok.extern.log4j.Log4j2;
import org.data.model.predict.entity.PredictGame;
import org.data.model.predict.enums.GameStatus;
import org.data.model.request.PredictGameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@Service
@Log4j2
public class PredictService {

    @Autowired
    private Random random;



   /* @Autowired
    private UserService userService;*/

    public PredictGame createGame(PredictGameRequest predictGameRequest) {
        int origin = 1;
        int bound = 101;
        int attempts = 5;
        return PredictGame.builder()
                .gameId(UUID.randomUUID())
                .origin(origin)
                .bound(bound)
                .userId(predictGameRequest.getUserId())
                .generatedRandomNumber(generateRandomNumber(origin, bound))
                .attemptsAllowed(attempts)
                .gameAttempts(new ArrayList<>(attempts))
                .attemptsRemaining(attempts)
                .gameStatus(GameStatus.START)
                .build();
    }

    private Integer generateRandomNumber(int origin, int bound) {
       return random.ints(1, 100).findFirst().getAsInt();
    }
}
