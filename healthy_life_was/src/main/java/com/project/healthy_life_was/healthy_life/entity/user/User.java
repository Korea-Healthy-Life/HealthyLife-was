package com.project.healthy_life_was.healthy_life.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "user_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "user_password", nullable = false)
    @JsonIgnoreProperties
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_nickname", nullable = false)
    private String userNickName;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name = "user_birth", nullable = false)
    private Date userBirth;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_phone", nullable = false)
    private String userPhone;

    @Column(name = "user_address", nullable = false)
    private String userAddress;

    @Column(name = "user_address_detail", nullable = false)
    private String userAddressDetail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender userGender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private MemberShip userMemberGrade = MemberShip.병아리;

}
