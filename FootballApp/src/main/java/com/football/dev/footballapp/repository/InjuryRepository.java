package com.football.dev.footballapp.repository;
import com.football.dev.footballapp.models.Injury;
import com.football.dev.footballapp.models.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface InjuryRepository extends JpaRepository<Injury,Long> {
    Page<Injury> findInjuriesByPlayerId(Long playerId,Pageable pageable);
}
