package com.football.dev.footballapp.models;

import jakarta.persistence.*;
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
  @ManyToOne
  @JoinColumn(name = "club_id")
  private Club club;
  public Integer matchesPlayed=0;
  public Integer wonMatches=0;
  public Integer drawMatches=0;
  public Integer lostMatches=0;
  public Integer goalScored=0;
  public Integer goalConceded=0;
  public Integer points=0;
  @ManyToOne
  @JoinColumn(name = "season_id")
  private Season season;

}
