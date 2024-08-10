package dev.hardika.UserService.service;

import dev.hardika.UserService.Entity.Role;
import dev.hardika.UserService.Entity.Session;
import dev.hardika.UserService.Entity.SessionStatus;
import dev.hardika.UserService.Entity.User;
import dev.hardika.UserService.Exception.InvalidCredentialException;
import dev.hardika.UserService.Exception.RoleNotFountException;
import dev.hardika.UserService.Exception.UserAlreadyExistException;
import dev.hardika.UserService.Exception.UserNotFoundException;
import dev.hardika.UserService.dto.LoginRequestDto;
import dev.hardika.UserService.dto.SingUpRequestDto;
import dev.hardika.UserService.dto.UserResponseDto;
import dev.hardika.UserService.dto.ValidationResponseDTO;
import dev.hardika.UserService.repository.RoleRepository;
import dev.hardika.UserService.repository.SessionRepository;
import dev.hardika.UserService.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.time.LocalDateTime;
import java.util.HashMap;
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

    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, SessionRepository sessionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto login(LoginRequestDto loginRequestDto) {
        //BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User saveUser = userRepository.findByEmailId(loginRequestDto.getEmail()).
                orElseThrow(() -> new UserNotFoundException("User with email: " + loginRequestDto.getEmail() + " does not exist"));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), saveUser.getPassword())) {
            throw new InvalidCredentialException("Password is incorrect");
        }

        // Token generation
        String userData = saveUser.getEmailId() + saveUser.getPassword() + LocalDateTime.now();
        String token = passwordEncoder.encode(userData);
        saveUser.setToken(token);
//              OR
        //RandomStringUtils randomStringUtils = new RandomStringUtils();
//        String token = RandomStringUtils.randomAscii(20);


        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH-TOKEN", token);


        UserResponseDto userResponseDto = UserResponseDto.from(saveUser);
        ResponseEntity<UserResponseDto> response = new ResponseEntity<>(
                userResponseDto,
                headers,
                HttpStatus.OK
        );

        Session session = new Session();
        session.setToken(token);
        session.setStatus(SessionStatus.ACTIVE);
        session.setUser(saveUser);
        sessionRepository.save(session);

        return response.getBody();
    }


    @Override
    public UserResponseDto singUp(SingUpRequestDto singUpRequestDto) {
        Optional<User> userOptional = userRepository.findByEmailId(singUpRequestDto.getEmail());
        if (!userOptional.isEmpty()) {
            throw new UserAlreadyExistException("User with " + singUpRequestDto.getEmail() + " already exists");
        }
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
    public ValidationResponseDTO validateToken(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUserId(token, userId);
        ValidationResponseDTO validationResponseDTO = new ValidationResponseDTO();
        if(sessionOptional.isEmpty()){
            validationResponseDTO.setSessionStatus(SessionStatus.INVALID);
            return validationResponseDTO;
        }
        Session session = sessionOptional.get();
        if (!session.getStatus().equals(SessionStatus.ACTIVE)){
            validationResponseDTO.setSessionStatus(SessionStatus.EXPIRED);
            return validationResponseDTO;
        }

        User user = session.getUser();
        //Optional<User> optionalUser = userRepository.findById(userId);
        UserResponseDto userDto = UserResponseDto.from(user);
        validationResponseDTO.setUserDTO(userDto);
        validationResponseDTO.setSessionStatus(SessionStatus.ACTIVE);
        return validationResponseDTO;

    }

//    @Override
//    public Optional<UserDTO> validateToken(String token) {
//        User savedUser = userRepository.findByToken(token).
//                orElseThrow(() -> new InvalidCredentialException("Token is not valid"));
//        return true;
//    }

    @Override
    public boolean logout(String token, Long userID){
        Optional<Session> optionalSession = sessionRepository.findByTokenAndUserId(token, userID);
        if(optionalSession.isEmpty()){
            return true;
        }
        Session session = optionalSession.get();
        session.setStatus(SessionStatus.LOGGED_OUT);
        sessionRepository.save(session);
        return true;

    //                orElseThrow(()-> new InvalidCredentialException("Token is not valid"));
//        savedUser.setToken(null);
//        userRepository.save(savedUser);//        User savedUser = userRepository.findByToken(token).authService.validateToken(requestDTO.getToken(), requestDTO.getUserId());
//        return true;
    }
}
