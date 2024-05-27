package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    Page<Player> findPlayersByClubAndIsDeletedFalseOrderByInsertDateTimeAsc(Club club, Pageable pageable);
}
