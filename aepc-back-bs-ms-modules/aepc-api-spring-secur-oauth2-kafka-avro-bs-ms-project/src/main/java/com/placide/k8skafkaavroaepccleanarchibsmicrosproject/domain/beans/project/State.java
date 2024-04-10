package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.project;

public enum State {
    STATE1("ongoing"),
    STATE2("terminated"),
    STATE3("aborted"),
    STATE4("outdated");
    private final String projectState;

    State(String projectState) {
        this.projectState = projectState;
    }

    public String getProjectState() {
        return projectState;
    }
}
