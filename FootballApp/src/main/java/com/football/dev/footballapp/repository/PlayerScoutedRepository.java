package com.football.dev.footballapp.repository;

import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.PlayerScouted;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerScoutedRepository extends JpaRepository<PlayerScouted, Long> {
}
