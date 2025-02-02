package com.project.healthy_life_was.healthy_life.dto.user.response;

import com.project.healthy_life_was.healthy_life.entity.deliverAddress.DeliverAddress;
import com.project.healthy_life_was.healthy_life.entity.user.Gender;
import com.project.healthy_life_was.healthy_life.entity.user.MemberShip;
import com.project.healthy_life_was.healthy_life.entity.user.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfoResponseDto {

    private String username;

    private String  name;

    private String userNickName;

    private String userEmail;

    private String userPhone;

    private Date userBirth;

    private Gender userGender;

    private MemberShip userMemberGrade;

    private String addressDetail;

    public UserInfoResponseDto(User user, DeliverAddress deliverAddress) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.userNickName = user.getUserNickName();
        this.userBirth = user.getUserBirth();
        this.userGender = user.getUserGender();
        this.userEmail = user.getUserEmail();
        this.userPhone = user.getUserPhone();
        this.addressDetail = deliverAddress.getAddressDetail();
        this.userMemberGrade = user.getUserMemberGrade();
    }
}
