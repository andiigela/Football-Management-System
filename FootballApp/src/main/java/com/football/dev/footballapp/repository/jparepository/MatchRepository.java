package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Round;
import com.football.dev.footballapp.models.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match,Long> {
    Page<Match> findMatchesByRoundId(Long roundId, Pageable pageable);
    Optional<Match> findByIdAndRoundId(Long matchId, Long roundId);
}
