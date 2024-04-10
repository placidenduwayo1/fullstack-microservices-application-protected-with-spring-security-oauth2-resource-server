package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.models;

import lombok.*;
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
    private String companyId;
}
