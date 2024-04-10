package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.project;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Project {
    private String projectId;
    private String name;
    private String description;
    private int priority;
    private String state;
    private String createdDate;

    @Override
    public String toString() {
        return "Project-API: [" +
                "project-id:'" + projectId + '\'' +
                ", project-name:'" + name + '\'' +
                ", description: '" + description + '\'' +
                ", priority: " + priority +
                ", state:'" + state + '\'' +
                ", created-date='" + createdDate + '\'' +
                ']';
    }
}
