package com.football.dev.footballapp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "round")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Round extends BaseEntity{
  public String name;

     private LocalDateTime start_date;
      private LocalDateTime end_date;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

   @OneToMany(mappedBy = "round")
   private List<Match> matches;



  public Round(LocalDateTime start_date, LocalDateTime end_date, Season season){
        this.start_date = start_date;
        this.end_date = end_date;
        this.season = season;
    }

}
