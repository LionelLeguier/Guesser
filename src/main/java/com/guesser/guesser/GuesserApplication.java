package com.guesser.guesser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.guesser.guesser", "Flag.modele"})
public class GuesserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuesserApplication.class, args);
    }

}
