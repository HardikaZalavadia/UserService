package dev.hardika.UserService.service;

import dev.hardika.UserService.dto.UserResponseDto;

public interface UserService {
    UserResponseDto getUserDetails(Long userId);
    UserResponseDto setUserRole(Long userId, String roleName);
}
