package dev.hardika.UserService.dto;

import dev.hardika.UserService.Entity.Role;
import dev.hardika.UserService.Entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponseDto {
    private String name;
    private String email;
    private String token;
    private List<RoleResponseDto> roles;

    public static UserResponseDto from(User user){
        if(user == null){
            return null;
        }
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.name = user.getName();
        userResponseDto.email = user.getEmailId();
        userResponseDto.token = user.getToken();
        userResponseDto.roles = new ArrayList<>();

        for(Role role : user.getRole()){
            RoleResponseDto roleResponseDto = new RoleResponseDto();
            roleResponseDto.setRoleName(role.getRoleName());
            roleResponseDto.setDesc(role.getDescription());
            roleResponseDto.setId(role.getId());
            userResponseDto.roles.add(roleResponseDto);
        }
        return userResponseDto;
    }

    public static User from(UserResponseDto userResponseDto){
        User user = new User();
        user.setName(userResponseDto.getName());
        user.setEmailId(userResponseDto.getEmail());
        List<Role> roles1 = new ArrayList<>();
        for(RoleResponseDto responseDto : userResponseDto.getRoles()){
            Role role = new Role();
            role.setRoleName(responseDto.getRoleName());
            role.setDescription(responseDto.getDesc());
            roles1.add(role);
        }
        user.setRole(roles1);
        return user;
    }
}
