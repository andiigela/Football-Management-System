package com.football.dev.footballapp.repository;
import com.football.dev.footballapp.models.Injury;
import org.springframework.data.jpa.repository.JpaRepository;
public interface InjuryRepository extends JpaRepository<Injury,Long> {
}
