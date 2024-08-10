package dev.hardika.UserService.service;


import dev.hardika.UserService.dto.LoginRequestDto;
import dev.hardika.UserService.dto.SingUpRequestDto;
import dev.hardika.UserService.dto.UserResponseDto;
import dev.hardika.UserService.dto.ValidationResponseDTO;

public interface AuthService {
    UserResponseDto login(LoginRequestDto loginResponseDto);
    UserResponseDto singUp(SingUpRequestDto singUpResponseDto);
    ValidationResponseDTO validateToken(String token, Long userId);
    boolean logout(String token,Long userId);

}
