package dev.hardika.UserService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SingUpRequestDto {
    private String name;
    private String email;
    private String password;
    private UUID roleId;
}
