package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.MatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchEventRepository extends JpaRepository<MatchEvent,Long> {
  List<MatchEvent> findAllByMatchId(Long id);
}
