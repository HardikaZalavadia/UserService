package dev.hardika.UserService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequestDto {
    private Long userId;
    private String token;
}
