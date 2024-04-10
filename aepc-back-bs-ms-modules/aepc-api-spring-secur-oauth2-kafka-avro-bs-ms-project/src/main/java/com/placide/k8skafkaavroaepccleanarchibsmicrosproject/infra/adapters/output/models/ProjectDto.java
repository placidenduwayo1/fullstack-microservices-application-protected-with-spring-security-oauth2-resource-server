package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.models;

import lombok.Builder;
import lombok.Data;
@Builder @Data
public class ProjectDto {
    private String name;
    private String description;
    private int priority;
    private String state;
    private String employeeId;
    private String companyId;
}
