package com.football.dev.footballapp.repository;

import com.football.dev.footballapp.models.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League,Long> {
    Optional<League> findByName(String name);
}
