package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Round;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

    Page<Round> findRoundsBySeasonIdOrderByInsertDateTimeDesc(Long seasonId, Pageable pageable);
    Optional<Round> findByIdAndSeasonId(Long roundId, Long seasonId);

}
