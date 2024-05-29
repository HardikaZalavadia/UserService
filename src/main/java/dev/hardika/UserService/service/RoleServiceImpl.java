package dev.hardika.UserService.service;

import dev.hardika.UserService.Entity.Role;
import dev.hardika.UserService.dto.RoleRequestDto;
import dev.hardika.UserService.dto.RoleResponseDto;
import dev.hardika.UserService.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleResponseDto createRole(RoleRequestDto requestDto) {
        Role role = new Role();
        role.setRoleName(requestDto.getRoleName());
        role.setDescription(requestDto.getDesc());
        roleRepository.save(role);
        return RoleResponseDto.from(role);
    }
}
