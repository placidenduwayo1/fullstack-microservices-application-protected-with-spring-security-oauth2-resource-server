package fr.placide.springsecurityservice.exceptionshandler;

import fr.placide.springsecurityservice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleSecurityException(Exception exc) {
        if (exc instanceof UserAlreadyExistsException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
        } else if (exc instanceof RoleAlreadyExistsException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
        } else if (exc instanceof AppRoleNotFoundException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
        } else if (exc instanceof AppUserNotFoundException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
        } else if (exc instanceof UserHasAlreadyThisRoleException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
        } else if (exc instanceof BadCredentialsException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
        } else if (exc instanceof AccessDeniedException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
        } else if (exc instanceof RegisteredPasswordNotMatchException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
        } else if (exc instanceof NewPasswordMuchSimilarToOldException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
        } else
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
