package com.football.dev.footballapp.repository.esrepository;

import com.football.dev.footballapp.models.ES.LeagueES;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LeagueRepositoryES extends ElasticsearchRepository<LeagueES, String> {
    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    List<LeagueES> findByName(String name);
}
