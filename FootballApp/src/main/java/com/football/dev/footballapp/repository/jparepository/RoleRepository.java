package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByDescription(String description);
}
