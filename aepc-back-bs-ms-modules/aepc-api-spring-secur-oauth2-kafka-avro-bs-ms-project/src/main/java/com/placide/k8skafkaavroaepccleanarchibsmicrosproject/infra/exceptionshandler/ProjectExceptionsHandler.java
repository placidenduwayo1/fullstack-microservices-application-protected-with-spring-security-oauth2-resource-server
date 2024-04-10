package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.exceptionshandler;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProjectExceptionsHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleProjectExceptions(Exception exc) {
        if (exc instanceof ProjectAlreadyExistsException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        } else if (exc instanceof ProjectPriorityInvalidException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof ProjectStateInvalidException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof ProjectFieldsEmptyException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof RemoteEmployeeApiException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof RemoteCompanyApiException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof ProjectNotFoundException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof ProjectAssignedRemoteEmployeeApiException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof ProjectAssignedRemoteCompanyApiException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof RemoteEmployeeStateUnauthorizedException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
