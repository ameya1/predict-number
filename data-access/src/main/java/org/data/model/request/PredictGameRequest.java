package org.data.model.request;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@ToString
@Builder
public class PredictGameRequest {
    Long userSerialId;
    UUID userId;
    UUID gameId;
    Integer predictedValue;

    public PredictGameRequest() {}
}
