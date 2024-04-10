package com.football.dev.footballapp.repository;

import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    List<Player> findPlayersByClub(Club club);
}
