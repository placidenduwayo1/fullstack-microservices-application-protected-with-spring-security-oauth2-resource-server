package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions;

public class ProjectAlreadyExistsException extends Exception {
    public ProjectAlreadyExistsException(String message) {
        super(message);
    }
}
