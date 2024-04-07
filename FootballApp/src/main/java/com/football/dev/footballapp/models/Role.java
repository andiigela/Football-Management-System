package com.football.dev.footballapp.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    private String description;
}
