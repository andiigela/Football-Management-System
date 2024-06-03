package com.football.dev.footballapp.repository.esrepository;

import com.football.dev.footballapp.models.ES.MatchES;
import com.football.dev.footballapp.models.ES.RoundES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RoundRepositoryES extends ElasticsearchRepository<RoundES, String> {
    Page<RoundES> findRoundsByStartDateBetween(Date startDate, Date endDate, Pageable pageable);
}
