package com.football.dev.footballapp.repository.esrepository;

import com.football.dev.footballapp.models.Club;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ClubRepositoryES extends ElasticsearchRepository<Club, Long> {

}
