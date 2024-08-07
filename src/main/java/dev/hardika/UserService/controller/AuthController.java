package dev.hardika.UserService.controller;

import dev.hardika.UserService.Entity.SessionStatus;
import dev.hardika.UserService.Entity.User;
import dev.hardika.UserService.dto.*;
import dev.hardika.UserService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        UserResponseDto respones = authService.login(loginRequestDto);
        return ResponseEntity.ok(respones);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody SingUpRequestDto singUpRequestDto){

        return ResponseEntity.ok(authService.singUp(singUpRequestDto));
    }


    @GetMapping("/singOut")
    public ResponseEntity<Boolean> logOut(@RequestHeader("Authorization") String authToken) {
        return ResponseEntity.ok(authService.logout(authToken));

    }

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponseDTO> validate(@RequestBody ValidationRequestDTO requestDTO) {
        Optional<UserResponseDto> userDTO = authService.validateToken(requestDTO.getToken(), requestDTO.getUserId());
        ValidationResponseDTO responseDTO = new ValidationResponseDTO();
        if (userDTO.isEmpty()) {
            responseDTO.setSessionStatus(SessionStatus.INVALID);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        responseDTO.setSessionStatus(SessionStatus.ACTIVE);
        responseDTO.setUserDTO(userDTO.get());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

//
//    @GetMapping("/validate")
//    public ResponseEntity<Boolean> validate(@RequestHeader("Authorization") String authToken){
//
//        return ResponseEntity.ok(authService.validateToken(authToken));
//    }

}
