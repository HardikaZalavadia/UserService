package dev.hardika.UserService.controller;

import dev.hardika.UserService.dto.RoleRequestDto;
import dev.hardika.UserService.dto.RoleResponseDto;
import dev.hardika.UserService.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleRequestDto requestDto){
        return ResponseEntity.ok(roleService.createRole(requestDto));
    }

}
