package com.football.dev.footballapp.models.ES;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@Document(indexName = "players")
public class PlayerES {
    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long dbId; //id e postgres
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Double)
    private Double height;
    @Field(type = FieldType.Double)
    private Double weight;
    @Field(type = FieldType.Integer)
    private Integer shirtNumber;
    @Field(type = FieldType.Text)
    private String imagePath;
    @Field(type = FieldType.Boolean)
    private boolean deleted;
    @Field(type = FieldType.Text)
    private String preferred_foot;
    @Field(type = FieldType.Text)
    private String position;
    @Field(type = FieldType.Text)
    private String contracts;
}
