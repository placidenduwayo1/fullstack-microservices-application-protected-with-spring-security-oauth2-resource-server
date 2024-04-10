package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.exceptionshandler;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionsHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleEmployeeExceptions(Exception exc) {
        if (exc instanceof EmployeeAlreadyExistsException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        } else if (exc instanceof EmployeeNotFoundException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof EmployeeEmptyFieldsException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof RemoteApiAddressNotLoadedException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof EmployeeStateInvalidException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof EmployeeTypeInvalidException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof EmployeeAlreadyAssignedProjectException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
