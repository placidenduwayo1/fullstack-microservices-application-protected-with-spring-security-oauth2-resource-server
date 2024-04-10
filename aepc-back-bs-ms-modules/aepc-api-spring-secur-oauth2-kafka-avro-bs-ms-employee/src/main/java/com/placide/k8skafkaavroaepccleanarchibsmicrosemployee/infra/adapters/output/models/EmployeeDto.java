package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class EmployeeDto {
    private String firstname;
    private String lastname;
    private String state;
    private String role;
    private String addressId;
}
