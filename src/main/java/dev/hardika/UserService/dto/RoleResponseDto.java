package dev.hardika.UserService.dto;

import dev.hardika.UserService.Entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoleResponseDto {
    private String roleName;
    private String desc;
    private Long id;

    public static RoleResponseDto from(Role role){
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        roleResponseDto.roleName = role.getRoleName();
        roleResponseDto.desc = role.getDescription();
        roleResponseDto.id = role.getId();
        return roleResponseDto;
    }
}
