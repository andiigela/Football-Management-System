package com.football.dev.footballapp.repository;

import com.football.dev.footballapp.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
