package dev.hardika.UserService.service;

import dev.hardika.UserService.dto.LoginRequestDto;
import dev.hardika.UserService.dto.SingUpRequestDto;
import dev.hardika.UserService.dto.UserResponseDto;

public interface UserService {
    UserResponseDto login(LoginRequestDto loginResponseDto);
    UserResponseDto singUp(SingUpRequestDto singUpResponseDto);
    boolean validateToken(String token);
    boolean logout(String token);

}
