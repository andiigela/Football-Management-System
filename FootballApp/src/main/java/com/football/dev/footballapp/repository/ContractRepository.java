package com.football.dev.footballapp.repository;
import com.football.dev.footballapp.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByIdAndPlayerId(Long contractId, Long playerId);
}
