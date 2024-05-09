package com.football.dev.footballapp.repository;

import com.football.dev.footballapp.models.League;
import com.football.dev.footballapp.models.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League,Long> {
    Optional<League> findByName(String name);
    Optional<League> findBySeasonsContaining(Season season);
}
