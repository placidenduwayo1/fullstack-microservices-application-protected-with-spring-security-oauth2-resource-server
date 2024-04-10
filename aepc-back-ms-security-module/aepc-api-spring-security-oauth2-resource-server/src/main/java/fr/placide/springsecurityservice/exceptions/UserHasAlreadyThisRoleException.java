package fr.placide.springsecurityservice.exceptions;

public class UserHasAlreadyThisRoleException extends Exception{
    public UserHasAlreadyThisRoleException(String message) {
        super(message);
    }
}
