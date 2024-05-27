package com.football.dev.footballapp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.football.dev.footballapp")
public class FootballAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(FootballAppApplication.class, args);
    }
}
