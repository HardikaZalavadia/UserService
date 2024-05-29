package dev.hardika.UserService.controller;

import dev.hardika.UserService.dto.LoginRequestDto;
import dev.hardika.UserService.dto.SingUpRequestDto;
import dev.hardika.UserService.dto.UserResponseDto;
import dev.hardika.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){

        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    @PostMapping("/singUp")
    public ResponseEntity singUp(@RequestBody SingUpRequestDto singUpRequestDto){

        return ResponseEntity.ok(userService.singUp(singUpRequestDto));
    }

    @GetMapping("/singOut")
    public ResponseEntity<Boolean> logOut(@RequestHeader("Authorization") String authToken) {
        return ResponseEntity.ok(userService.logout(authToken));

    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestHeader("Authorization") String authToken){

        return ResponseEntity.ok(userService.validateToken(authToken));
    }

}
