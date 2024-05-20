package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Club findClubByUserEmail(String email);
}
