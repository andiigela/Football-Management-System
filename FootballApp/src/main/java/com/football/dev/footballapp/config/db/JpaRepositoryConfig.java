package com.football.dev.footballapp.config.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.football.dev.footballapp.repository.jparepository")
public class JpaRepositoryConfig {
}
