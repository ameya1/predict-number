package org.predict;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.predict", "org.data"})
public class PredictGameMain {
    public static void main(String[] args) {
        SpringApplication.run(PredictGameMain.class);
    }
}