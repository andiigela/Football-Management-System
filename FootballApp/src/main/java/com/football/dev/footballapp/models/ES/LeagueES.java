package com.football.dev.footballapp.models.ES;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.Date;

@Document(indexName = "league")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueES {
    @Id
    private String id;
    //private Long dbId;
    private String name;

   // @Field(type = FieldType.Date)
    private Date startDate;

    //@Field(type = FieldType.Date)
    private Date endDate;

    private String description;
    public LeagueES(String name, Date startDate, Date endDate, String description){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
}
