package com.project.healthy_life_was.healthy_life.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.healthy_life_was.healthy_life.entity.user.Gender;
import com.project.healthy_life_was.healthy_life.entity.user.MemberShip;
import com.project.healthy_life_was.healthy_life.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpResponseDto {

    private Long userId;

    private String username;

    private String password;

    private String userNickName;

    private Date userBirth;

    private Gender userGender;

    private String userEmail;

    private String userPhone;

    private String userAddress;

    private String userAddressDetail;

    private MemberShip userMemberGrade;

    public SignUpResponseDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.userNickName = user.getUserNickName();
        this.userBirth = user.getUserBirth();
        this.userGender = user.getUserGender();
        this.userEmail = user.getUserEmail();
        this.userPhone = user.getUserPhone();
        this.userAddress = user.getUserAddress();
        this.userAddressDetail = user.getUserAddressDetail();
        this.userMemberGrade = user.getUserMemberGrade();
    }
}
