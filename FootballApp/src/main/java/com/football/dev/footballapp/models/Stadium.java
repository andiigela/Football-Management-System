package com.football.dev.footballapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "stadiums")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Stadium extends BaseEntity{
    private String name;
    private Integer capacity;
    @OneToOne
    @JoinColumn
    private Location location;

}
