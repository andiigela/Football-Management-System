package com.football.dev.footballapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false,updatable = false)
    public LocalDateTime insertDateTime;
    @Column(nullable = false,updatable = false)
    public Long insertUserId;
    @Column(nullable = false)
    public LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    public Long lastUpdateUserId;
    public Boolean isDeleted=false;


}
