package com.football.dev.footballapp.models;


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
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL)
    private List<Match> matches= new ArrayList<>();


}
