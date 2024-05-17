package dev.hardika.UserService.service;

import dev.hardika.UserService.Entity.Role;
import dev.hardika.UserService.Entity.User;
import dev.hardika.UserService.Exception.InvalidCredentialException;
import dev.hardika.UserService.Exception.RoleNotFountException;
import dev.hardika.UserService.Exception.UserNotFoundException;
import dev.hardika.UserService.dto.LoginRequestDto;
import dev.hardika.UserService.dto.SingUpRequestDto;
import dev.hardika.UserService.dto.UserResponseDto;
import dev.hardika.UserService.repository.RoleRepository;
import dev.hardika.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public UserResponseDto login(LoginRequestDto loginRequestDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User saveUser = userRepository.findByEmailId(loginRequestDto.getEmail()).
                orElseThrow(() -> new UserNotFoundException("User Not Found"));
        if (bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), saveUser.getPassword())) {
            String userData = saveUser.getEmail() + saveUser.getPassword() + LocalDateTime.now();
            String token = bCryptPasswordEncoder.encode(userData);
            saveUser.setToken(token);
        } else {
            throw new InvalidCredentialException("Token is not valid");
        }
        saveUser = userRepository.save(saveUser);
        return UserResponseDto.from(saveUser);
    }


    @Override
    public UserResponseDto singUp(SingUpRequestDto singUpRequestDto) {
        Role role = roleRepository.findById(singUpRequestDto.getRoleId()).
                orElseThrow(() -> new RoleNotFountException(" Role is not found"));

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setName(singUpRequestDto.getName());
        user.setEmail(singUpRequestDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(singUpRequestDto.getPassword()));
        user.setRole(List.of(role));

        return UserResponseDto.from(user);
    }

    @Override
    public boolean validateToken(String token) {
        User savedUser = userRepository.findByToken(token).
                orElseThrow(() -> new InvalidCredentialException("Token is not valid"));
        return true;
    }

    @Override
    public boolean logout(String token) {
        User savedUser = userRepository.findByToken(token).
                orElseThrow(()-> new InvalidCredentialException("Token is not valid"));
        savedUser.setToken(null);
        userRepository.save(savedUser);
        return true;
    }
}
