package com.football.dev.footballapp.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.football.dev.footballapp.models.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    public UserEntity(String firstName, String lastName, String email, String phone, String country,
                      Date birthDate, String address, String city, String postalCode, String gender, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.birthDate = birthDate;
        this.address = address;
        this.city = city;
        this.postal_code = postalCode;
        this.gender = getGenderFromString(gender);
        this.enabled = enabled;
    }
    public static Gender getGenderFromString(String gender) {
        try {
            return Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    private Long notificationsNumber=0L;
}
