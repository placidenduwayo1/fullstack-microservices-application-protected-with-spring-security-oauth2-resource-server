package fr.placide.springsecurityservice.exceptions;

public class RoleAlreadyExistsException extends Exception{
    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
