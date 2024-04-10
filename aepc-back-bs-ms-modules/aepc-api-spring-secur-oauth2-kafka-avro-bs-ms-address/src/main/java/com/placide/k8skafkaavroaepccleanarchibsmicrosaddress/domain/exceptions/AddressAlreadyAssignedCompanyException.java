package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions;


public class AddressAlreadyAssignedCompanyException extends Exception {
    public AddressAlreadyAssignedCompanyException(String messages) {
        super(messages);
    }
}
