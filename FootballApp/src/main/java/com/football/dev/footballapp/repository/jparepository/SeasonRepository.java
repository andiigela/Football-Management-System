package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
    Page<Season> findSeasonsByLeagueIdAndIsDeletedFalseOrderByInsertDateTimeDesc(Long leagueId, Pageable pageable);
    Optional<Season> findByIdAndLeagueId(Long seasonId, Long leagueId);


}
