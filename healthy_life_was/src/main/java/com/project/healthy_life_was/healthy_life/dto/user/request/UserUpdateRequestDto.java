package com.project.healthy_life_was.healthy_life.dto.user.request;

import com.project.healthy_life_was.healthy_life.dto.deliverAddress.DeliverAddressDto;
import com.project.healthy_life_was.healthy_life.entity.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
}