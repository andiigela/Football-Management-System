package com.football.dev.footballapp.repository;

import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.PlayerScouted;
import com.football.dev.footballapp.models.PlayerScoutingReports;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlayerScoutingReportRepository  extends JpaRepository<PlayerScoutingReports, Long> {
}
