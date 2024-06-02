package com.football.dev.footballapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "standings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Standing extends BaseEntity{
  private Integer numberOfStanding;
  private String picture;
  private String name;
  private Integer matchPlayed;
  private Integer wonGames;
  private Integer drawGames;
  private Integer loseGames;
  private Integer goalsScored;
  private Integer goalsAccepted;
  private Integer points;
  @ManyToOne
  private Season season;

}
