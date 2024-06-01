package com.football.dev.footballapp.repository.esrepository;

import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.models.ES.PlayerES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PlayerRepositoryES extends ElasticsearchRepository<PlayerES, String> {
    Page<PlayerES> findAllByDeletedFalseOrderByHeightAsc(PageRequest pageable);
    Page<PlayerES> findAllByDeletedFalseOrderByHeightDesc(PageRequest pageable);
    Page<PlayerES> findAllByDeletedFalseOrderByWeightAsc(PageRequest pageable);
    Page<PlayerES> findAllByDeletedFalseOrderByWeightDesc(PageRequest pageable);
    PlayerES findByDbId(Long id);
}
