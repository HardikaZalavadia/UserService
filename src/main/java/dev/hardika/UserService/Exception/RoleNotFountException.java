package dev.hardika.UserService.Exception;

public class RoleNotFountException extends RuntimeException {
    public RoleNotFountException() {
    }

    public RoleNotFountException(String message) {
        super(message);
    }
}
