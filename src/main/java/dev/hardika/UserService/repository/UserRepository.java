package dev.hardika.UserService.repository;

import dev.hardika.UserService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailId(String emailId);
    Optional<User> findByToken(String token);
}
