package fr.placide.springsecurityservice.exceptions;

public class PasswordAndPasswordConfirmationNotMatchException extends Exception{
    public PasswordAndPasswordConfirmationNotMatchException(String message) {
        super(message);
    }
}
