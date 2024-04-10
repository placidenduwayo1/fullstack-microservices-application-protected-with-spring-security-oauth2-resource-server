package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions;

public class CompanyAlreadyExistsException extends Exception {
    public CompanyAlreadyExistsException(String message) {
        super(message);
    }
}
