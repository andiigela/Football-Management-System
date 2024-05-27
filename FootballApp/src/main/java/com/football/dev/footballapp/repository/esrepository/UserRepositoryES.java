package com.football.dev.footballapp.repository.esrepository;

import com.football.dev.footballapp.models.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepositoryES extends ElasticsearchRepository<UserEntity, Long> {
    @Override
    <S extends UserEntity> S save(S entity);
}
