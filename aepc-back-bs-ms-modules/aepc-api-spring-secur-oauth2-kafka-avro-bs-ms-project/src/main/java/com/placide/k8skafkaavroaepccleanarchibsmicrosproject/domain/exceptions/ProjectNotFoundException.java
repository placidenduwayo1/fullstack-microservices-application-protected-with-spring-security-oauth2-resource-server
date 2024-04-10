package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions;

public class ProjectNotFoundException extends Exception {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
