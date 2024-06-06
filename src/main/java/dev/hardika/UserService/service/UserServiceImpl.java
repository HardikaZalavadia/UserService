package dev.hardika.UserService.service;

import dev.hardika.UserService.Entity.Role;
import dev.hardika.UserService.Entity.User;
import dev.hardika.UserService.Exception.UserNotFoundException;
import dev.hardika.UserService.dto.UserResponseDto;
import dev.hardika.UserService.repository.RoleRepository;
import dev.hardika.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Override

    public UserResponseDto getUserDetails(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser==null){
            throw new UserNotFoundException("User not found");
        }
        User user = optionalUser.get();
        UserResponseDto userResponseDto = UserResponseDto.from(user);
        return userResponseDto;

    }

    @Override
    public UserResponseDto setUserRole(Long userId, String roleName) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser==null){
            throw new UserNotFoundException("User not found");
        }

        Role role = new Role();
        role.setRoleName(roleName);
        role.setDescription("this is role for "+roleName);
        roleRepository.save(role);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = optionalUser.get();
        user.setRole(roles);

        return UserResponseDto.from(user);
    }
}
