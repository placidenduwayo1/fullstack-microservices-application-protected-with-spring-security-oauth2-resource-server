package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.excetionshandler;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AddressExceptionsHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleAddressExceptions(Exception exc) {
        if (exc instanceof AddressAlreadyExistsException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof AddressFieldsInvalidException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof AddressNotFoundException) {
            return new ResponseEntity<>(exc.getMessage(),
                    HttpStatus.NOT_FOUND);
        } else if (exc instanceof AddressCityNotFoundException) {
            return new ResponseEntity<>(exc.getMessage(),
                    HttpStatus.NOT_FOUND);
        } else if (exc instanceof AddressAlreadyAssignedCompanyException) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } else if (exc instanceof AddressAlreadyAssignedEmployeeException) {
            return new ResponseEntity<>(exc.getMessage(),
                    HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

    }
}
