package com.football.dev.footballapp.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "is_deleted=false")
public class Club extends BaseEntity{

    private String name;
    @OneToOne
    private Stadium stadium;
    private Integer foundedYear;
    private String city;
    private String website;
    @ManyToOne
    private League league;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

  //shtoni atribute tjera te nevojshme

    public Club(String name, Stadium stadium, Integer foundedYear, String city, String website, League league){
        this.name=name;
        this.stadium = stadium;
        this.foundedYear = foundedYear;
        this.city = city;
        this.website = website;
        this.league = league;

    }
}
