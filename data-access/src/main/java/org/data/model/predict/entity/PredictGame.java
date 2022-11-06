package org.data.model.predict.entity;

import lombok.*;
import org.data.model.predict.enums.GameResult;
import org.data.model.predict.enums.GameStatus;
import org.data.model.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class PredictGame {

    static {
        KEY = "game:";
    }

    private Long id;
    private UUID gameId;
    private UUID userId;
    private User user;
    private LocalDateTime createdAt;
    private Integer generatedRandomNumber;
    private Integer origin;
    private Integer bound;
    private Integer attemptsAllowed;
    private Integer totalAttempts;
    private Integer attemptsRemaining;
    private List<GameAttempt> gameAttempts;
    private GameStatus gameStatus;
    private GameResult gameResult;
    public final static String KEY;

    public void reduceAttempts() {
        this.attemptsRemaining--;
    }
}
