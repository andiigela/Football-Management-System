package com.football.dev.footballapp.models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL)
    private List<Match> matches= new ArrayList<>();



}
