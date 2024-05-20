package com.football.dev.footballapp.repository.jparepository;
import com.football.dev.footballapp.models.Injury;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InjuryRepository extends JpaRepository<Injury,Long> {
    Page<Injury> findInjuriesByPlayerId(Long playerId,Pageable pageable);
    Optional<Injury> findByIdAndPlayerId(Long injuryId, Long playerId);

}
