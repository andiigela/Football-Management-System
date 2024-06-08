package com.football.dev.footballapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
public class LeagueDTO {
    private Long id;
    private String idEs;
    private String name;
    private Integer founded;
    private Date startDate;
    private Date endDate;
    private String description;
    private String picture;

    public LeagueDTO(Long id,String name ,Integer founded, String description,String picture){
      this.id=id;
      this.name=name;
      this.founded=founded;
      this.description=description;
      this.picture=picture;
    }

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
        this.id = dbId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public LeagueDTO(Long dbId, String idEs, String name, Date startDate, Date endDate, String description) {
        this.id = dbId;
        this.idEs = idEs;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
}
