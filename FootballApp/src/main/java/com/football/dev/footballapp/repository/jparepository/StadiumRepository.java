package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Match;
import com.football.dev.footballapp.models.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium,Long> {
}
