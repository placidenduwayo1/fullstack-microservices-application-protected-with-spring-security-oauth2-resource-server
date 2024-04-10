package fr.placide.springsecurityservice.exceptions;

public class AppRoleNotFoundException extends Exception{
    public AppRoleNotFoundException(String message) {
        super(message);
    }
}
