package com.football.dev.footballapp.repository;

import com.football.dev.footballapp.models.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
}
