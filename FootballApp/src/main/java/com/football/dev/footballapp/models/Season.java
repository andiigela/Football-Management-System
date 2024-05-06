package com.football.dev.footballapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "season")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Season extends BaseEntity{
    private String name;
    @OneToMany
    private List<Match> matches;

    public Season(String name){
        this.name = name;
    }
    //clubs
    //table
    //statistics
}
