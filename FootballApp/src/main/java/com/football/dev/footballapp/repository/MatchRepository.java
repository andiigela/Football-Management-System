package com.football.dev.footballapp.repository;

import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match,Long> {
}
