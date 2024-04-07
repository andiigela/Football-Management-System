package com.football.dev.footballapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Table(name = "contracts")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Contract extends BaseEntity{
    @OneToOne
    private UserEntity userEntity;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    private Club club;
    private Double salary;

}
