package com.football.dev.footballapp.repository.jparepository;

import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.Standing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StandingRepository extends JpaRepository<Standing,Long> {
  List<Standing> findAllBySeasonId(Long id);

  @Query(nativeQuery = true, value = "SELECT standings.club_id from standings where season_id = :id")
  List<Club> findClubs(@Param("id") int id );

  @Query("SELECT s.club FROM Standing s WHERE s.season.id = :seasonId")
  List<Club> findAllClubsBySeasonId(Long seasonId);

  Optional<Standing> findBySeasonIdAndClubId(Long season_id , Long club_id);
}
