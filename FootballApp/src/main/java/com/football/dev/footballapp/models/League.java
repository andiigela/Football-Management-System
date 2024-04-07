package com.football.dev.footballapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Table(name = "leagues")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class League extends BaseEntity{
    private String name;
    private Date start_date;
    private Date end_date;
    private String description;
}
