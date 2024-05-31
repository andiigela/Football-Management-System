package com.football.dev.footballapp.models.ES;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.DateFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Document(indexName = "matches")
public class MatchES {

    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long dbId; //id e postgres

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date matchDate; // Match date and time

    @Field(type = FieldType.Integer)
    private Integer homeTeamScore; // Home team score

    @Field(type = FieldType.Integer)
    private Integer awayTeamScore; // Away team score

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = java.sql.Timestamp.valueOf(matchDate);
    }
}
