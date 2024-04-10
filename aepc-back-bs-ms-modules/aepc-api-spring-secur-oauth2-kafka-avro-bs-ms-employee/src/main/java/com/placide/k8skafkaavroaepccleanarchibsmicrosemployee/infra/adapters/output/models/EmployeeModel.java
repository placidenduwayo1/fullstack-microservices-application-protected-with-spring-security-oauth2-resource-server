package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.models;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.models.AddressModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "employees")
public class EmployeeModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String employeeId;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String hireDate;
    private String state;
    private String role;
    private String addressId;
    @Transient
    private AddressModel address;
}
