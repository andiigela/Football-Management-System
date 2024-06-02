package com.football.dev.footballapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Date start_date;
     private Date end_date;

    @ManyToOne
    private League league;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private List<Round> rounds = new ArrayList<>();

    public Season(String name, League league){
        this.name = name;
        this.league = league;
    }

}
