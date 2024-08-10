package dev.hardika.UserService.security.service;

import dev.hardika.UserService.repository.UserRepository;
import dev.hardika.UserService.security.model.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import dev.hardika.UserService.Entity.User;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // here username is emailId because we are login with emailId and password
        Optional<User> user = userRepository.findByEmailId(username);

        if(user.isEmpty()){
            throw new UsernameNotFoundException(username + " not found");
        }
        return new CustomUserDetails(user.get());
    }
}
