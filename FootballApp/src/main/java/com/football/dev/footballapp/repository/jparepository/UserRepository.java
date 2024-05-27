package com.football.dev.footballapp.repository;

import com.football.dev.footballapp.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String username);
    Boolean existsByEmail(String username);
    List<UserEntity> findAll();
    List<UserEntity> findByRoleDescription(String role);
    Page<UserEntity> findByRoleDescriptionAndIsDeleted(String role, boolean isDeleted, Pageable pageable);
}
