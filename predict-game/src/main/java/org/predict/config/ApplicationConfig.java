package org.predict.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.data.model.predict.entity.PredictGame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ApplicationConfig {

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }

    @Bean
    public Map<String, PredictGame> gameMap() {
        return new ConcurrentHashMap<>();
    }

}
