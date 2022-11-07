package org.predict.controller;

import lombok.extern.log4j.Log4j2;
import org.data.model.request.PredictGameRequest;
import org.data.model.response.PredictGameResponse;
import org.predict.service.GameService;
import org.predict.service.PredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/predict")
@Log4j2
public class PredictController {

    @Autowired
    private PredictService predictService;

    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public ResponseEntity<PredictGameResponse> start(@RequestBody PredictGameRequest predictGameRequest) {
        try {
            return ResponseEntity.ok(gameService.newGame(predictGameRequest));
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            PredictGameResponse response = PredictGameResponse.builder().message("Error creating game").build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/game")
    public ResponseEntity<PredictGameResponse> predict(@RequestBody PredictGameRequest predictGameRequest) {
        try {
            return ResponseEntity.ok(gameService.attempt(predictGameRequest));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok(PredictGameResponse.builder().message(e.getMessage()).build());
        }
    }
}
