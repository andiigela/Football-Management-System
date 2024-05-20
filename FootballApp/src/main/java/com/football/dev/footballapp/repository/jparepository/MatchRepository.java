package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match,Long> {
}
