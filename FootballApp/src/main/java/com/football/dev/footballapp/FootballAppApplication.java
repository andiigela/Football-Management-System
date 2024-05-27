package com.football.dev.footballapp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.football.dev.footballapp")

public class FootballAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(FootballAppApplication.class, args);
    }
}
