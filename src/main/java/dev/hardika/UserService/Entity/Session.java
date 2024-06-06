package dev.hardika.UserService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
public class Session extends BaseModel{
    private String token;
    private Instant expiresAt;
    @Enumerated(EnumType.STRING)
    private SessionStatus status;
    @ManyToOne
    private User user;
}
