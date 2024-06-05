package com.football.dev.footballapp.models.ES;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "user")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntityES {
    @Id
    private String id; //id e elasticsearch

    @Field(type = FieldType.Long)
    private Long dbId;

    @Field(type = FieldType.Text)
    private String email;

    @Field(type = FieldType.Boolean)
    private boolean enabled;

    @Field(type = FieldType.Boolean)
    private boolean isDeleted;
}
