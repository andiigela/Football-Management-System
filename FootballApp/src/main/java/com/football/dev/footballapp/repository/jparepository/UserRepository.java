package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String username);
    Boolean existsByEmail(String username);
    List<UserEntity> findAll();
    List<UserEntity> findByRoleDescription(String role);
    List<UserEntity> findByRoleDescriptionAndIsDeleted(String role, boolean isDeleted);
    List<UserEntity> findUserEntitiesByRoleDescription(String description);
}
