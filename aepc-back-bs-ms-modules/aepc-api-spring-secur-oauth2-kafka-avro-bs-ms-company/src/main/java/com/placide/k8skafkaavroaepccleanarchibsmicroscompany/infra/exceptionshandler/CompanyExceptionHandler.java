package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.exceptionshandler;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CompanyExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleCompanyExceptions(Exception exc) {
        if (exc instanceof CompanyNotFoundException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        } else if (exc instanceof CompanyEmptyFieldsException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof CompanyAlreadyExistsException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        } else if (exc instanceof CompanyTypeInvalidException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        } else if (exc instanceof RemoteApiAddressNotLoadedException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        } else if (exc instanceof CompanyAlreadyAssignedRemoteProjectsException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        } else if (exc instanceof RemoteAddressAlreadyHoldsCompanyException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
