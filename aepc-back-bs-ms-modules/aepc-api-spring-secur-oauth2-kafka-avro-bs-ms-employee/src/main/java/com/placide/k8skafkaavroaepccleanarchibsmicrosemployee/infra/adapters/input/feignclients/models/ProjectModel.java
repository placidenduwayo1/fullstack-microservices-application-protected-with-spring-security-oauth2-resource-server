package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ProjectModel {
    private String projectId;
    private String name;
    private String description;
    private int priority;
    private String state;
    private String createdDate;
    private String employeeId;
}
