package com.football.dev.footballapp.models;

import jakarta.persistence.Entity;

@Entity
public class Location extends BaseEntity{
    private String city;
    private String country;

}
