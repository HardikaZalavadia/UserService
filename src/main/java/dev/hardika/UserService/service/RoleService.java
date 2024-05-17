package dev.hardika.UserService.service;

import dev.hardika.UserService.dto.RoleRequestDto;
import dev.hardika.UserService.dto.RoleResponseDto;

public interface RoleService {
    RoleResponseDto createRole(RoleRequestDto requestDto);
}
