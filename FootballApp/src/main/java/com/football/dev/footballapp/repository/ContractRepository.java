package com.football.dev.footballapp.repository;
import com.football.dev.footballapp.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
