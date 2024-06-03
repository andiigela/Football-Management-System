package com.football.dev.footballapp.models.ES;

import com.football.dev.footballapp.models.Season;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Document(indexName = "rounds")
public class RoundES {
    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long dbId;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date startDate;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date endDate;
    @Field(type = FieldType.Long)
    private Long season;
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = java.sql.Timestamp.valueOf(startDate);
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = java.sql.Timestamp.valueOf(endDate);
    }
}
