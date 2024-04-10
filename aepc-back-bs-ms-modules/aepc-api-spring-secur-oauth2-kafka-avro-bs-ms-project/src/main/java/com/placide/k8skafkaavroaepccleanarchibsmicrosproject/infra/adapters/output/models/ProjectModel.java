package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.models;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Builder @Data @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "projects")
public class ProjectModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String projectId;
    private String name;
    private String description;
    private int priority;
    private String state;
    private String createdDate;
    private String employeeId;
    @Transient
    private Employee employee;
    private String companyId;
    @Transient
    private Company company;
}
