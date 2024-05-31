package com.football.dev.footballapp.repository.esrepository;

import com.football.dev.footballapp.models.ES.MatchES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface MatchRepositoryES extends ElasticsearchRepository<MatchES, String> {

    Page<MatchES> findMatchesByMatchDateAndHomeTeamScoreAndAwayTeamScore(Date matchDate, Integer homeTeamScore, Integer awayTeamScore, Pageable pageable);
}
