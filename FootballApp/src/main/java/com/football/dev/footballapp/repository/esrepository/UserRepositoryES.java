package com.football.dev.footballapp.repository.esrepository;

import com.football.dev.footballapp.models.ES.LeagueES;
import com.football.dev.footballapp.models.ES.UserEntityES;
import com.football.dev.footballapp.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepositoryES extends ElasticsearchRepository<UserEntityES, String> {
    List<UserEntityES> findByEmailStartingWithAndIsDeletedFalse(String email);
    UserEntityES findByDbId(Long id);
}
