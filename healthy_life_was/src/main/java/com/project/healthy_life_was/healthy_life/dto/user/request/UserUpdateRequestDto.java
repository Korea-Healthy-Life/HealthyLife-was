package com.project.healthy_life_was.healthy_life.dto.user.request;

import com.project.healthy_life_was.healthy_life.dto.deliverAddress.DeliverAddressDto;
import com.project.healthy_life_was.healthy_life.entity.user.Gender;
import com.project.healthy_life_was.healthy_life.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class UserUpdateRequestDto {

    private String name;

    private String userNickName;

    private String userEmail;

    private String userPhone;

    private Date userBirth;

    private Gender userGender;

    private DeliverAddressDto deliverAddressDto;

    public UserUpdateRequestDto(User user, DeliverAddressDto deliverAddressRequestDto) {
        this.name = user.getName();
        this.userNickName = user.getUserNickName();
        this.userEmail = user.getUserEmail();
        this.userPhone = user.getUserPhone();
        this.userBirth = user.getUserBirth();
        this.userGender = user.getUserGender();
        this.deliverAddressDto = deliverAddressRequestDto;
    }
}
