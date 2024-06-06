package dev.hardika.UserService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationRequestDTO {
    private Long userId;
    private String token;
}

