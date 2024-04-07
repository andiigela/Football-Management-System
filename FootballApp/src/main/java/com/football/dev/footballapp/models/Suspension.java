package com.football.dev.footballapp.models;

import com.football.dev.footballapp.models.enums.SuspensionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Table(name = "suspension")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Suspension extends BaseEntity {
    @ManyToOne
    private Player player;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String status;
    @Enumerated(EnumType.STRING)
    private SuspensionStatus suspensionStatus;
}
