package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions;

public class AddressAlreadyExistsException extends Exception {
    public AddressAlreadyExistsException(String message) {
        super(message);
    }
}
