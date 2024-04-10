package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.employee;

public enum EmpRole {
    DIR("director"),
    HR("hr"),
    HR_A("hr-assistant"),
    COM("com-manager"),
    TAR("talent-acquisition-referent"),
    SE("software-engineer"),
    BS("business-manager"),
    APPREN("apprentice"),
    TRAIN("training");
    private final String role;

    EmpRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
