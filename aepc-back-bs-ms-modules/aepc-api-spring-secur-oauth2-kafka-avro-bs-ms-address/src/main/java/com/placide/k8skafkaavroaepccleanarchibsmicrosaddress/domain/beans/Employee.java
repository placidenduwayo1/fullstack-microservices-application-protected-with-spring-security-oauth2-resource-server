package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans;

import lombok.Builder;
import lombok.Data;

@Data @Builder
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
        return "Employee-API:[" +
                "employee-id:'" + employeeId + '\'' +
                ", firstname:'" + firstname + '\'' +
                ", lastname:'" + lastname + '\'' +
                ", email:'" + email + '\'' +
                ", here-date:'" + hireDate + '\'' +
                ", employee-state:'" + state + '\'' +
                ", type:'" + role + '\'' +
                ']';
    }

}
