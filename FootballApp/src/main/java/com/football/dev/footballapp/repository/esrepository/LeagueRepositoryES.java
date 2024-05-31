package com.football.dev.footballapp.repository.esrepository;

import com.football.dev.footballapp.models.ES.LeagueES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LeagueRepositoryES extends ElasticsearchRepository<LeagueES, String> {

    Page<LeagueES> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name, Pageable pageable);
    LeagueES findByDbId(Long id);
}
