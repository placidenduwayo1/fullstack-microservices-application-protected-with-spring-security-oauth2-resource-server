package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.input.feignclients.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class EmployeeModel {
    private String employeeId;
    private String firstname;
    private String lastname;
    private String email;
    private String hireDate;
    private String state;
    private String role;
}
