package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.project;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Project {
    private String projectId;
    private String name;
    private String description;
    private int priority;
    private String state;
    private String createdDate;
    private String companyId;

    @Override
    public String toString() {
        return "Project-API[" +
                "project-Id:'" + projectId + '\'' +
                ", project-name:'" + name + '\'' +
                ", description:'" + description + '\'' +
                ", priority=" + priority +
                ", project-state:'" + state + '\'' +
                ", created-date:'" + createdDate + '\'' +
                ']';
    }
}
