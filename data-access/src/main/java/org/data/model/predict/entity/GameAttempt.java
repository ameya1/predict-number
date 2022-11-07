package org.data.model.predict.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.data.model.predict.enums.InputNumberLevel;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class GameAttempt {
    private Long id;
    private UUID attemptId;
    private UUID gameId;
    private PredictGame predictGame;
    private LocalDateTime createdAt;
    private Integer inputValue;
    private InputNumberLevel inputNumberLevel;

    @Override
    public String toString() {
        return "GameAttempt{" +
                "id=" + id +
                " predictGameId=" + gameId +
                " inputNumberLevel=" + inputNumberLevel.toString() +
                ", createdAt=" + createdAt +
                ", inputValue=" + inputValue +
                '}';
    }
}
