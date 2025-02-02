package com.project.healthy_life_was.healthy_life.service.implement;

import com.project.healthy_life_was.healthy_life.common.constant.ResponseMessage;
import com.project.healthy_life_was.healthy_life.dto.ResponseDto;
import com.project.healthy_life_was.healthy_life.dto.user.request.UserUpdateRequestDto;
import com.project.healthy_life_was.healthy_life.dto.user.response.UserInfoResponseDto;
import com.project.healthy_life_was.healthy_life.entity.deliverAddress.DeliverAddress;
import com.project.healthy_life_was.healthy_life.entity.user.User;
import com.project.healthy_life_was.healthy_life.repository.DeliverAddressRepository;
import com.project.healthy_life_was.healthy_life.repository.UserRepository;
import com.project.healthy_life_was.healthy_life.service.UserService;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final DeliverAddressRepository deliverAddressRepository;

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
}
