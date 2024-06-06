package dev.hardika.UserService.dto;

import dev.hardika.UserService.Entity.SessionStatus;
import dev.hardika.UserService.Entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationResponseDTO {
    private UserResponseDto userDTO;
    private SessionStatus sessionStatus;
}
