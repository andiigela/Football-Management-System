package com.football.dev.footballapp.models;

import com.football.dev.footballapp.models.enums.TypeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "match-events")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
@AllArgsConstructor
public class MatchEvent extends BaseEntity {

  public Integer minute;
  @Enumerated(value = EnumType.STRING)
  public TypeStatus type;
  @ManyToOne
  @JoinColumn(name = "club_id")
  public Club club;
  @ManyToOne
  @JoinColumn(name = "player_id")
  public Player player;
  @ManyToOne
  @JoinColumn(name = "assisted_player_id", nullable = true)
  public Player assisted = null;
  @ManyToOne
  @JoinColumn(name = "substitute_player_id", nullable = true)
  public Player substitutePlayer = null;
  public boolean isPenalty = false;
  public boolean isOwnGoal = false;
  public boolean redCard = false;
  public boolean yellowCard = false;
  private String description;

  @ManyToOne
  @JoinColumn(name = "match_id")
  private Match match;
}
