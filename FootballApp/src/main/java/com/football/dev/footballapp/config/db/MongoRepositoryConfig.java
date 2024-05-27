package com.football.dev.footballapp.config.db;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.football.dev.footballapp.repository.mongorepository")
public class MongoRepositoryConfig {
}
