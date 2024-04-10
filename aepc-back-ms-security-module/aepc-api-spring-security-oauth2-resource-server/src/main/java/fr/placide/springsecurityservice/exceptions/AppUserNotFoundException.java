package fr.placide.springsecurityservice.exceptions;

public class AppUserNotFoundException extends Exception{
    public AppUserNotFoundException(String message) {
        super(message);
    }
}
