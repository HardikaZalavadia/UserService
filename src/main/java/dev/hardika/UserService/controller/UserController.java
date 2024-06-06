package dev.hardika.UserService.controller;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserDetails(@PathVariable("{id}") Long userId) {
        UserResponseDto userResponseDto = userService.getUserDetails(userId);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/{id}/setrole")
    public ResponseEntity<UserResponseDto> serUserRole(@PathVariable("/{id}") Long userId, @RequestBody String roleName) {
        UserResponseDto userResponseDto = userService.setUserRole(userId,roleName);
        return ResponseEntity.ok(userResponseDto);
    }
}
