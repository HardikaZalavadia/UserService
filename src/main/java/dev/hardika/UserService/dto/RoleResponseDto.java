package dev.hardika.UserService.dto;

import dev.hardika.UserService.Entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDto {
    private String roleName;
    private String desc;

    public static RoleResponseDto from(Role role){
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        roleResponseDto.setRoleName(role.getRoleName());
        roleResponseDto.setDesc(role.getDescription());
        return roleResponseDto;
    }
}
