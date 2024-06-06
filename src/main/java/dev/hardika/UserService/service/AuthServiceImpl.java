package dev.hardika.UserService.service;

import dev.hardika.UserService.Entity.Role;
import dev.hardika.UserService.Entity.Session;
import dev.hardika.UserService.Entity.SessionStatus;
import dev.hardika.UserService.Entity.User;
import dev.hardika.UserService.Exception.InvalidCredentialException;
import dev.hardika.UserService.Exception.RoleNotFountException;
import dev.hardika.UserService.Exception.UserNotFoundException;
import dev.hardika.UserService.dto.LoginRequestDto;
import dev.hardika.UserService.dto.SingUpRequestDto;
import dev.hardika.UserService.dto.UserResponseDto;
import dev.hardika.UserService.repository.RoleRepository;
import dev.hardika.UserService.repository.SessionRepository;
import dev.hardika.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public UserResponseDto login(LoginRequestDto loginRequestDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User saveUser = userRepository.findByEmailId(loginRequestDto.getEmail()).
                orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), saveUser.getPassword())) {
            throw new InvalidCredentialException("Token is not valid");
        }

        String userData = saveUser.getEmailId() + saveUser.getPassword() + LocalDateTime.now();
        String token = bCryptPasswordEncoder.encode(userData);
        saveUser.setToken(token);

        Session session = new Session();
        session.setToken(token);
        session.setStatus(SessionStatus.ACTIVE);
        session.setUser(saveUser);
        sessionRepository.save(session);

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
        user.setEmailId(singUpRequestDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(singUpRequestDto.getPassword()));
        user.setRole(List.of(role));

        return UserResponseDto.from(userRepository.save(user));
    }
    public Optional<UserResponseDto> validateToken(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUserId(token, userId);
        if(sessionOptional.isEmpty()){
            return Optional.empty();
        }
        Session session = sessionOptional.get();
        if (!session.getStatus().equals(SessionStatus.ACTIVE)){
            return Optional.empty();
        }
        User user = session.getUser();
        //Optional<User> user = userRepository.findById(userId);
        UserResponseDto userDto = UserResponseDto.from(user);
        return Optional.of(userDto);
    }

//    @Override
//    public Optional<UserDTO> validateToken(String token) {
//        User savedUser = userRepository.findByToken(token).
//                orElseThrow(() -> new InvalidCredentialException("Token is not valid"));
//        return true;
//    }

    @Override
    public boolean logout(String token) {
        User savedUser = userRepository.findByToken(token).
                orElseThrow(()-> new InvalidCredentialException("Token is not valid"));
        savedUser.setToken(null);
        userRepository.save(savedUser);
        return true;
    }
}
