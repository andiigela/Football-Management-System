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
    private Date startDate;
    private Date endDate;
    private String description;

    public LeagueDTO(String name, Date startDate, Date endDate, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
    public LeagueDTO(String idEs,String name, Date startDate, Date endDate, String description) {
        this.idEs = idEs;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
    public LeagueDTO(Long dbId,String name, Date startDate, Date endDate, String description) {
        this.dbId = dbId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public LeagueDTO(Long dbId, String idEs, String name, Date startDate, Date endDate, String description) {
        this.dbId = dbId;
        this.idEs = idEs;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
}
