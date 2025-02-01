package com.project.healthy_life_was.healthy_life.service;

import com.project.healthy_life_was.healthy_life.dto.ResponseDto;
import com.project.healthy_life_was.healthy_life.dto.user.request.UserUpdateRequestDto;
import com.project.healthy_life_was.healthy_life.dto.user.response.UserInfoResponseDto;

public interface UserService {
    ResponseDto<UserInfoResponseDto> getUserInfo(String username);
    ResponseDto<UserInfoResponseDto> updateUserInfo(String username, UserUpdateRequestDto dto);
}