package com.football.dev.footballapp.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.football.dev.footballapp.models.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Where(clause = "is_deleted=false")
public class UserEntity extends BaseEntity {

    private String firstName;
    private String lastName;
    @Column(unique = true,nullable = false)
    private String email;
    private String password;
    private boolean enabled;
    private String phone;
    private String country;
    private Date birthDate;
    private String profile_picture;
    private String address;
    private String city;
    private String postal_code;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Club club;

    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private RefreshToken refreshToken;

}
