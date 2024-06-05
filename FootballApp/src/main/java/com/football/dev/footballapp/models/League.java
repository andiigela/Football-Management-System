package com.football.dev.footballapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "leagues")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class League extends BaseEntity{
    private String name;
    private Integer founded;
    private String description;
    private String picture;
    public League(String name,Integer founded,String description,String picture){
      this.name =name;
      this.founded =founded;
      this.description =description;
      this.picture =picture;
    }

     @OneToMany(mappedBy = "league")
    public List<Season> seasonList;

}
