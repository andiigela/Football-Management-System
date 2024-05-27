package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer,Long> {
}
