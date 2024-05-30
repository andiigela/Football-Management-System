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

import java.util.Date;

@Document(indexName = "league")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueES {
    @Id
    private String id; //id e elasticsearch

    @Field(type = FieldType.Long)
    private Long dbId; //id e postgres

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Date)
    private Date startDate;

    @Field(type = FieldType.Date)
    private Date endDate;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Boolean)
    private boolean isDeleted;

    public LeagueES(String id, Long dbId, String name, Date startDate, Date endDate, String description) {
        this.id = id;
        this.dbId = dbId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

}
