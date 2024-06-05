package com.football.dev.footballapp.models;

import com.football.dev.footballapp.models.enums.MatchStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "matches")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Match extends BaseEntity {
  @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
  private List<MatchEvent> matchEvents;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Club homeTeamId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Club awayTeamId;
    private LocalDateTime matchDate;
    @OneToOne
    private Stadium stadium;
    private String result;
    private Integer homeTeamScore=0;
    private Integer awayTeamScore=0;
    @ManyToOne
   @JoinColumn(name = "round_id")
     private Round round;
   @Enumerated(value = EnumType.STRING)
   private MatchStatus matchStatus;

  public Match(Long id , Long id2){
    this.homeTeamId.id=id;
    this.awayTeamId.id=id;

  }

  public Match(Club homeTeamId, Club awayTeamId, LocalDateTime matchDate,
                 String result, Integer homeTeamScore,
                 Integer awayTeamScore, Round round) {
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.matchDate = matchDate;
        this.result = result;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.round = round;
    }
}
