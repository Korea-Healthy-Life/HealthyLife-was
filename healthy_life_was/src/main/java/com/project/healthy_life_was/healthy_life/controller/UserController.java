package com.project.healthy_life_was.healthy_life.controller;

import com.project.healthy_life_was.healthy_life.common.constant.ApiMappingPattern;
import com.project.healthy_life_was.healthy_life.dto.ResponseDto;
import com.project.healthy_life_was.healthy_life.dto.user.request.UserUpdateRequestDto;
import com.project.healthy_life_was.healthy_life.dto.user.response.UserInfoResponseDto;
import com.project.healthy_life_was.healthy_life.security.PrincipalUser;
import com.project.healthy_life_was.healthy_life.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiMappingPattern.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private ResponseEntity<ResponseDto<UserInfoResponseDto>> getUserInfo (
            @AuthenticationPrincipal PrincipalUser principalUser
            ){
        String username = principalUser.getUsername();
        ResponseDto<UserInfoResponseDto> response = userService.getUserInfo(username);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    private ResponseEntity<ResponseDto<UserInfoResponseDto>> updateUserInfo (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody UserUpdateRequestDto dto
            ){
        String username = principalUser.getUsername();
        ResponseDto<UserInfoResponseDto> response = userService.updateUserInfo(username, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

}
