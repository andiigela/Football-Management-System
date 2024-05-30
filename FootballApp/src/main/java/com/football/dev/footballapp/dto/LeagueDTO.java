package com.football.dev.footballapp.dto;

import com.football.dev.footballapp.models.Season;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class LeagueDTO {
    private Long dbId;
    private String idEs;
    private String name;
    private Date start_date;
    private Date end_date;
    private String description;

    public LeagueDTO(String name, Date start_date, Date end_date, String description) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
    }
    public LeagueDTO(String idEs,String name, Date start_date, Date end_date, String description) {
        this.idEs = idEs;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
    }
    public LeagueDTO(Long dbId,String name, Date start_date, Date end_date, String description) {
        this.dbId = dbId;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
    }

    public LeagueDTO(Long dbId, String idEs, String name, Date start_date, Date end_date, String description) {
        this.dbId = dbId;
        this.idEs = idEs;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
    }
}
