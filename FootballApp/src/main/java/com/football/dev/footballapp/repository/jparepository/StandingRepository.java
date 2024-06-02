package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Standing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandingRepository extends JpaRepository<Standing,Long> {
}
