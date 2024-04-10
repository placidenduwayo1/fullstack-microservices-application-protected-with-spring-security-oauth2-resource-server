package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.project;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.employee.Employee;

public class Project {
    private String projectId;
    private String name;
    private String description;
    private int priority;
    private String state;
    private String createdDate;
    private String employeeId;
    private Employee employee;
    private String companyId;
    private Company company;

    private Project(ProjectBuilder builder) {
        this.projectId = builder.projectId;
        this.name = builder.name;
        this.description = builder.description;
        this.priority = builder.priority;
        this.state = builder.state;
        this.createdDate = builder.createdDate;
        this.employeeId = builder.employeeId;
        this.employee = builder.employee;
        this.companyId = builder.companyId;
        this.company = builder.company;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Project[" +
                "id:'" + projectId + '\'' +
                ", name:'" + name + '\'' +
                ", description:'" + description + '\'' +
                ", priority:" + priority +
                ", state:'" + state + '\'' +
                ", created-date:" + createdDate +
                ", employee:" + employee +
                ", company:" + company +
                ']';
    }

    public static class ProjectBuilder {
        private String projectId;
        private String name;
        private String description;
        private int priority;
        private String state;
        private String createdDate;
        private String employeeId;
        private Employee employee;
        private String companyId;
        private Company company;

        public ProjectBuilder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public ProjectBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProjectBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProjectBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public ProjectBuilder state(String state) {
            this.state = state;
            return this;
        }

        public ProjectBuilder createdDate(String createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public ProjectBuilder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public ProjectBuilder employee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public ProjectBuilder companyId(String companyId) {
            this.companyId = companyId;
            return this;
        }

        public ProjectBuilder company(Company company) {
            this.company = company;
            return this;
        }

        public Project build(){
            return new Project(this);
        }
    }
}
