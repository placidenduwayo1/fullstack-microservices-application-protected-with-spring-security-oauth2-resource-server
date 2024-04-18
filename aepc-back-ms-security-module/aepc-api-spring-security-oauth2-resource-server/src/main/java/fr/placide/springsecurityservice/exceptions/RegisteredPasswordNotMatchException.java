package fr.placide.springsecurityservice.exceptions;

public class RegisteredPasswordNotMatchException extends Exception {
    public RegisteredPasswordNotMatchException(String message) {
        super(message);
    }
}
