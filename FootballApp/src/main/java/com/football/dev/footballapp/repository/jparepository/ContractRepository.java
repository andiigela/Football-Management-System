package com.football.dev.footballapp.repository.jparepository;
import com.football.dev.footballapp.models.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByIdAndPlayerId(Long contractId, Long playerId);
    Page<Contract> findContractsByPlayerId(Long playerId, Pageable pageable);
}
