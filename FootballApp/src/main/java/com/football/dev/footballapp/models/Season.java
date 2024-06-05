package com.football.dev.footballapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "season")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Season extends BaseEntity{
    private String name;
    private Boolean currentSeason =false;
    private LocalDateTime start_date;
     private LocalDateTime end_date;
     private Long headToHead;
     private Long numberOfStanding;

  @OneToOne
  public Club champion;

  @OneToMany(mappedBy = "season")
  public List<Round> rounds;

  @OneToMany
  public List<Club> relegatedTeams;

  @OneToMany
  public List<Club> promotedTeams;

  @ManyToOne
  @JoinColumn(name = "league_id")
  private League league;

    public Season(String name, League league){
        this.name = name;
        this.league = league;
    }
    public Season(String name,LocalDateTime start_date,LocalDateTime end_date,Long headToHead,Long numberOfStanding,League league){
      this.name=name;
      this.start_date=start_date;
      this.end_date=end_date;
      this.headToHead=headToHead;
      this.numberOfStanding=numberOfStanding;
      this.league=league;

    }

}
