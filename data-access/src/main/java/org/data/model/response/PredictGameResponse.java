package org.data.model.response;

import lombok.*;
import org.data.model.predict.enums.GameResult;
import org.data.model.predict.enums.GameStatus;
import org.data.model.predict.enums.InputNumberLevel;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class PredictGameResponse {
    private UUID gameId;
    private Long userSerialId;
    private UUID userId;
    private Integer origin;
    private Integer bound;
    private Integer attemptsRemaining;
    private Integer attemptsAllowed;
    private InputNumberLevel inputNumberLevel;
    private GameStatus gameStatus;
    private GameResult gameResult;
    private String message;
}
