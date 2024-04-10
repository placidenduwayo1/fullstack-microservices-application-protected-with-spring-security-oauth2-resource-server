package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.mappers;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.avrobeans.ProjectAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.feignclients.models.CompanyModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.feignclients.models.EmployeeModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.models.ProjectDto;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.models.ProjectModel;
import org.springframework.beans.BeanUtils;

public class Mapper {
    private Mapper() {
    }

    public static ProjectModel fromTo(Project bean) {
        ProjectModel model = ProjectModel.builder().build();
        BeanUtils.copyProperties(bean, model);
        return model;
    }

    public static Project fromTo(ProjectModel model) {
        Project bean = new Project.ProjectBuilder().build();
        BeanUtils.copyProperties(model, bean);
        return bean;
    }

    public static Project fromTo(ProjectDto dto) {
        Project bean = new Project.ProjectBuilder().build();
        BeanUtils.copyProperties(dto, bean);
        return bean;
    }

    public static EmployeeModel fromTo(Employee bean) {
        EmployeeModel model = EmployeeModel.builder().build();
        BeanUtils.copyProperties(bean, model);
        return model;
    }

    public static Employee fromTo(EmployeeModel model) {
        Employee bean = Employee.builder().build();
        BeanUtils.copyProperties(model, bean);
        return bean;
    }

    public static CompanyModel fromTo(Company bean) {
        CompanyModel model = CompanyModel.builder().build();
        BeanUtils.copyProperties(bean, model);
        return model;
    }

    public static Company fromTo(CompanyModel model) {
        Company bean = Company.builder().build();
        BeanUtils.copyProperties(model, bean);
        return bean;
    }

    public static ProjectAvro fromBeanToAvro(Project project) {
        com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.avrobeans.Employee employeeAvro =
                com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.avrobeans.Employee.newBuilder()
                        .setEmployeeId(project.getEmployeeId())
                        .setFirstname(project.getEmployee().getFirstname())
                        .setLastname(project.getEmployee().getLastname())
                        .setEmail(project.getEmployee().getEmail())
                        .setHireDate(project.getEmployee().getHireDate())
                        .setState(project.getEmployee().getState())
                        .setRole(project.getEmployee().getRole())
                        .build();
        com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.avrobeans.Company companyAvro =
                com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.avrobeans.Company.newBuilder()
                        .setCompanyId(project.getCompanyId())
                        .setName(project.getCompany().getName())
                        .setAgency(project.getCompany().getAgency())
                        .setType(project.getCompany().getType())
                        .setConnectedDate(project.getCompany().getConnectedDate())
                        .build();

        return ProjectAvro.newBuilder()
                .setProjectId(project.getProjectId())
                .setName(project.getName())
                .setDescription(project.getDescription())
                .setPriority(project.getPriority())
                .setState(project.getState())
                .setCreatedDate(project.getCreatedDate())
                .setEmployeeId(project.getEmployeeId())
                .setEmployee(employeeAvro)
                .setCompanyId(project.getCompanyId())
                .setCompany(companyAvro)
                .build();
    }

    public static Project fromAvroToBean(ProjectAvro projectAvro) {
        Employee employee = Employee.builder()
                .employeeId(projectAvro.getEmployeeId())
                .firstname(projectAvro.getEmployee().getFirstname())
                .lastname(projectAvro.getEmployee().getLastname())
                .email(projectAvro.getEmployee().getEmail())
                .hireDate(projectAvro.getEmployee().getHireDate())
                .state(projectAvro.getEmployee().getState())
                .role(projectAvro.getEmployee().getRole())
                .build();

        Company company = Company.builder()
                .companyId(projectAvro.getCompanyId())
                .name(projectAvro.getCompany().getName())
                .agency(projectAvro.getCompany().getAgency())
                .type(projectAvro.getCompany().getType())
                .connectedDate(projectAvro.getCompany().getConnectedDate())
                .build();


        return new Project.ProjectBuilder()
                .projectId(projectAvro.getProjectId())
                .name(projectAvro.getName())
                .description(projectAvro.getDescription())
                .priority(projectAvro.getPriority())
                .state(projectAvro.getState())
                .createdDate(projectAvro.getCreatedDate())
                .employeeId(projectAvro.getEmployeeId())
                .employee(employee)
                .companyId(projectAvro.getCompanyId())
                .company(company)
                .build();
    }
}
