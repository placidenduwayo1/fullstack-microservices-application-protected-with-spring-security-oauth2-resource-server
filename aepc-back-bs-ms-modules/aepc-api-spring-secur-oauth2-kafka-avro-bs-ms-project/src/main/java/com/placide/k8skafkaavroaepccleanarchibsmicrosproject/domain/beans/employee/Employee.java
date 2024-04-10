package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.employee;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Employee {
    private String employeeId;
    private String firstname;
    private String lastname;
    private String email;
    private String hireDate;
    private String state;
    private String role;
    @Override
    public String toString() {
        return "Employee-Api [" +
                "id:'" + employeeId + '\'' +
                ", firstname:'" + firstname + '\'' +
                ", lastname:'" + lastname + '\'' +
                ", email:'" + email + '\'' +
                ", hire-date:'" + hireDate + '\'' +
                ", state:'" + state + '\'' +
                ", type:'" + role + '\'' +
                ']';
    }
}