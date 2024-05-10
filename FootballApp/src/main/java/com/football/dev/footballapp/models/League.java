package com.football.dev.footballapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "leagues")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class League extends BaseEntity{
    private String name;
    private Date start_date;
    private Date end_date;
    private String description;
    @OneToMany(mappedBy = "league")
//    @JsonManagedReference
    private List<Season> seasons = new ArrayList<>();

    public League(String name, Date start_date, Date end_date, String description){

        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
    }
}
