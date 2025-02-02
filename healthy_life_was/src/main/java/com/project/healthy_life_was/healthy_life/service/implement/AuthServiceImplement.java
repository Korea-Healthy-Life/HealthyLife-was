package com.project.healthy_life_was.healthy_life.service.implement;

import com.project.healthy_life_was.healthy_life.common.constant.ResponseMessage;
import com.project.healthy_life_was.healthy_life.dto.ResponseDto;
import com.project.healthy_life_was.healthy_life.dto.auth.request.LoginRequestDto;
import com.project.healthy_life_was.healthy_life.dto.auth.request.SignUpRequestDto;
import com.project.healthy_life_was.healthy_life.dto.auth.response.LoginResponseDto;
import com.project.healthy_life_was.healthy_life.dto.auth.response.SignUpResponseDto;
import com.project.healthy_life_was.healthy_life.entity.deliverAddress.DeliverAddress;
import com.project.healthy_life_was.healthy_life.entity.user.Gender;
import com.project.healthy_life_was.healthy_life.entity.user.User;
import com.project.healthy_life_was.healthy_life.provider.JwtProvider;
import com.project.healthy_life_was.healthy_life.repository.AuthRepository;
import com.project.healthy_life_was.healthy_life.repository.DeliverAddressRepository;
import com.project.healthy_life_was.healthy_life.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImplement implements AuthService {
    private final AuthRepository authRepository;
    private final DeliverAddressRepository deliverAddressRepository;
    private final BCryptPasswordEncoder bCryptpasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseDto<SignUpResponseDto> signUp(SignUpRequestDto dto) {
       SignUpResponseDto data = null;

       String username = dto.getUsername();
       String password = dto.getPassword();
       String confirmPassword = dto.getConfirmPassword();
       String name = dto.getName();
       String userNickName = dto.getUserNickName();
       Date userBirth = dto.getUserBirth();
       String userEmail = dto.getUserEmail();
       String userPhone = dto.getUserPhone();
       Gender userGender = dto.getUserGender();
       String address = dto.getAddress();
       String addressDetail = dto.getAddressDetail();

        if (username == null || username.trim().isEmpty() || !username.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,15}$")) {
            return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "username");
        }

        if (password == null || password.trim().isEmpty() || !password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,16}$")) {
            return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "password");
        }

        if (confirmPassword == null || confirmPassword.trim().isEmpty() || !confirmPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,16}$")) {
           return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "confirmPassword");
       }

       if (!password.equals(confirmPassword)) {
           return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD + "confirmPassword");
       }

       if (name == null || name.isEmpty() || !name.matches("^[가-힣a-zA-Z]{2,10}$")) {
           return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "name");
       }

       if (userNickName == null || userNickName.trim().isEmpty() || !userNickName.matches("^[가-힣a-zA-Z\\d]{3,10}$")) {
           return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "userNickName");
       }

       if (userPhone == null || userPhone.isEmpty() || !userPhone.matches("^01[016789]\\d{7,8}$")) {
           return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "userPhone");
       }

       if (userEmail == null || userEmail.isEmpty() || !userEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
           return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "userEmail");
       }

       if (userGender == null) {
           return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "userGender");
       }

       if (userBirth == null) {
           return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "userBirth");
       }

       if (authRepository.existsByUsername(username)) {
           return ResponseDto.setFailed(ResponseMessage.EXIST_USER_NAME);
       }

//       if (authRespository.existsByUserEmail(userEmail)) {
//           return ResponseDto.setFailed(ResponseMessage.EXIST_USER_EMAIL);
//       }

       if (authRepository.existsByUserNickName(userNickName)) {
           return ResponseDto.setFailed(ResponseMessage.EXIST_USER_NICK_NAME);
       }

       try {
           String encodePassword = bCryptpasswordEncoder.encode(password);
           User user = User.builder()
                    .username(username)
                    .password(encodePassword)
                    .name(name)
                    .userNickName(userNickName)
                    .userBirth(userBirth)
                    .userPhone(userPhone)
                    .userEmail(userEmail)
                    .userGender(userGender)
                    .build();
           User savedUser = authRepository.save(user);
           DeliverAddress deliveraddress = DeliverAddress.builder()
                   .user(savedUser)
                   .address(address)
                   .addressDetail(addressDetail)
                   .build();
           deliverAddressRepository.save(deliveraddress);
            data = new SignUpResponseDto(user, deliveraddress);
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
       }
       return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<LoginResponseDto> login(LoginRequestDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        LoginResponseDto data = null;

        if (username == null || username.isEmpty()) {
            return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "username");
        }

        if (password == null || password.isEmpty()) {
            return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL + "password");
        }

        try {
            User user = authRepository.findByUsername(username);
            DeliverAddress deliverAddress = deliverAddressRepository.findByUser_UserId(user.getUserId());

            if (user == null) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_USER);
            }
            if (!bCryptpasswordEncoder.matches(password, user.getPassword())) {
                return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
            }

            String token = jwtProvider.generateJwtToken(username, user.getUserNickName());
            int exprTime = jwtProvider.getExpiration();

            data = new LoginResponseDto(user, deliverAddress, token, exprTime);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.SIGN_IN_FAIL);
        }

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<Boolean> duplicateUserName(String username) {
        try {
            boolean result = authRepository.existsByUsername(username);

            if (result == true) {
                return ResponseDto.setSuccess(ResponseMessage.DUPLICATED_USER_NAME, result);
            } else {
                return ResponseDto.setSuccess(ResponseMessage.SUCCESS, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    public ResponseDto<Boolean> duplicateUserNickName(String userNickName) {
        try {
            boolean result = authRepository.existsByUserNickName(userNickName);

            if (result == true) {
                return ResponseDto.setSuccess(ResponseMessage.DUPLICATED_USER_NAME, result);
            } else {
                return ResponseDto.setSuccess(ResponseMessage.SUCCESS, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
