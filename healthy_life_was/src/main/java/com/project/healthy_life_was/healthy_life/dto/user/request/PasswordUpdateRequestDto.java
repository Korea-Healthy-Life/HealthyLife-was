package com.project.healthy_life_was.healthy_life.dto.user.request;

import lombok.Getter;

@Getter
public class PasswordUpdateRequestDto {
    private String currentPassword;
    private String userPassword;
    private String confirmUserPassword;
}
