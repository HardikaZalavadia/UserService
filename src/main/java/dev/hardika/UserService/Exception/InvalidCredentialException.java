package dev.hardika.UserService.Exception;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String tokenIsNotValid) {
        super(tokenIsNotValid);
    }
    public InvalidCredentialException(){

    }
}
