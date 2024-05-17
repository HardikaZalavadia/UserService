package dev.hardika.UserService.controller;

import dev.hardika.UserService.dto.RoleRequestDto;
import dev.hardika.UserService.dto.RoleResponseDto;
import dev.hardika.UserService.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class RoleController {
    RoleService roleService;
    @PostMapping("/role")
    public ResponseEntity<RoleResponseDto> createToken(@RequestBody RoleRequestDto requestDto){
        return ResponseEntity.ok(roleService.createRole(requestDto));
    }
}
