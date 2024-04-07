package com.football.dev.footballapp.repository;

import com.football.dev.footballapp.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Long> {
}
