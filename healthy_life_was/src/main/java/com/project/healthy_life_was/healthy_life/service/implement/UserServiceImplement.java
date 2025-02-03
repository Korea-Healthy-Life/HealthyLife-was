package com.project.healthy_life_was.healthy_life.service.implement;

import com.project.healthy_life_was.healthy_life.common.constant.ResponseMessage;
import com.project.healthy_life_was.healthy_life.dto.ResponseDto;
import com.project.healthy_life_was.healthy_life.dto.user.request.PasswordUpdateRequestDto;
import com.project.healthy_life_was.healthy_life.dto.user.request.UserDeleteRequestDto;
import com.project.healthy_life_was.healthy_life.dto.user.request.UserUpdateRequestDto;
import com.project.healthy_life_was.healthy_life.dto.user.response.UserInfoResponseDto;
import com.project.healthy_life_was.healthy_life.entity.deliverAddress.DeliverAddress;
import com.project.healthy_life_was.healthy_life.entity.user.User;
import com.project.healthy_life_was.healthy_life.repository.DeliverAddressRepository;
import com.project.healthy_life_was.healthy_life.provider.JwtProvider;
import com.project.healthy_life_was.healthy_life.repository.UserRepository;
import com.project.healthy_life_was.healthy_life.service.UserService;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final DeliverAddressRepository deliverAddressRepository;
    private final BCryptPasswordEncoder bCryptpasswordEncoder;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public boolean checkPassword(String currentPassword, String encodedPassword) {
        return passwordEncoder.matches(currentPassword, encodedPassword);
    }

    @Override
    public ResponseDto<UserInfoResponseDto> getUserInfo(String username) {
        UserInfoResponseDto data = null;
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InternalException(ResponseMessage.NOT_EXIST_USER));
        DeliverAddress deliverAddress = deliverAddressRepository.findByUser_UserId(user.getUserId());

        data = new UserInfoResponseDto(user, deliverAddress);

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<UserInfoResponseDto> updateUserInfo(String username, UserUpdateRequestDto dto) {
        UserInfoResponseDto data = null;
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InternalException(ResponseMessage.NOT_EXIST_USER));
        DeliverAddress deliverAddress = deliverAddressRepository.findByUser_UserId(user.getUserId());
        User updatedUser = user.toBuilder()
                .name(dto.getName())
                .userNickName(dto.getUserNickName())
                .userEmail(dto.getUserEmail())
                .userPhone(dto.getUserPhone())
                .userBirth(dto.getUserBirth())
                .userGender(dto.getUserGender())
                .build();

        DeliverAddress updateDeliverAddress = DeliverAddress.builder()
                .address(dto.getDeliverAddressDto().getAddress())
                .addressDetail(dto.getDeliverAddressDto().getAddressDetail())
                .build();

        userRepository.save(updatedUser);

        data = new UserInfoResponseDto(updatedUser, updateDeliverAddress);

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<Void> updatePwByMyPage(String username, PasswordUpdateRequestDto dto) {
        String currentPassword = dto.getCurrentPassword();
        String password = dto.getUserPassword();
        String confirmUserPassword = dto.getConfirmUserPassword();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InternalException(ResponseMessage.NOT_EXIST_USER));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseDto.setFailed(ResponseMessage.INCORRECT_CURRENT_PASSWORD);
        }

        if (!password.equals(confirmUserPassword)) {
            return ResponseDto.setFailed(ResponseMessage.PASSWORD_MISMATCH);
        }

        String encodePassword = bCryptpasswordEncoder.encode(password);
        User updatedUser = user.toBuilder()
                .password(encodePassword)
                .build();
        userRepository.save(updatedUser);

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }

    @Override
    public ResponseDto<Void> updatePwByEmailToken(String token, PasswordUpdateRequestDto dto) {
        String password = dto.getUserPassword();
        String confirmUserPassword = dto.getConfirmUserPassword();
        String username = jwtProvider.getUsernameFromJwt(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InternalException(ResponseMessage.NOT_EXIST_USER));

        if (!password.equals(confirmUserPassword)) {
            return ResponseDto.setFailed(ResponseMessage.PASSWORD_MISMATCH);
        }

        String encodePassword = bCryptpasswordEncoder.encode(password);
        User updatedUser = user.toBuilder()
                .password(encodePassword)
                .build();
        userRepository.save(updatedUser);

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }

    @Override
    public ResponseDto<Void> deleteUser(String username, UserDeleteRequestDto dto) {
        String password = dto.getUserPassword();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InternalException(ResponseMessage.NOT_EXIST_USER));

        if (!bCryptpasswordEncoder.matches(password, user.getPassword())) {
            return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
        }
        userRepository.delete(user);

        return null;
    }
}