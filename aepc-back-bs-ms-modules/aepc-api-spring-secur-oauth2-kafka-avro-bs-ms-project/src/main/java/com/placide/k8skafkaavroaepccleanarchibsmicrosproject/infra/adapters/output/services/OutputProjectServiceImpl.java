package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.services;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.avrobeans.ProjectAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.ProjectNotFoundException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output.OutputProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output.OutputRemoteApiCompanyService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output.OutputRemoteApiEmployeeService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.feignclients.models.CompanyModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.feignclients.models.EmployeeModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.feignclients.proxies.CompanyServiceProxy;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.feignclients.proxies.EmployeeServiceProxy;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.mappers.Mapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.models.ProjectModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OutputProjectServiceImpl implements OutputProjectService, OutputRemoteApiEmployeeService, OutputRemoteApiCompanyService {
    private final ProjectRepository repository;
    private final EmployeeServiceProxy employeeServiceProxy;
    private final CompanyServiceProxy companyServiceProxy;

    public OutputProjectServiceImpl(ProjectRepository repository,
                                    @Qualifier(value = "employeeserviceproxy") EmployeeServiceProxy employeeServiceProxy,
                                    @Qualifier(value = "companyserviceproxy") CompanyServiceProxy companyServiceProxy) {
        this.repository = repository;
        this.employeeServiceProxy = employeeServiceProxy;
        this.companyServiceProxy = companyServiceProxy;
    }

    @Override
    @KafkaListener(topics = "avro-projects-created", groupId = "project-group-id")
    public Project consumeKafkaEventProjectCreate(@Payload ProjectAvro projectAvro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Project project = Mapper.fromAvroToBean(projectAvro);
        log.info("employee to delete:<{}> consumed from topic:<{}>",project,topic);
        return project;
    }

    @Override
    public Project saveProject(Project project) {
        ProjectAvro projectAvro = Mapper.fromBeanToAvro(project);
        Project consumed = consumeKafkaEventProjectCreate(projectAvro,"avro-projects-created");
        ProjectModel saved = repository.save(Mapper.fromTo(consumed));
        return Mapper.fromTo(saved);
    }

    @Override
    public Project getProject(String projectId) throws ProjectNotFoundException {
        ProjectModel model = repository.findById(projectId).orElseThrow(
                ()->new ProjectNotFoundException("Project not found Exception"));
        return Mapper.fromTo(model);
    }

    @Override
    public List<Project> loadProjectByInfo(String name, String desc, String state, String employeeId, String companyId) {
        return mapToBean(repository.findByNameAndDescriptionAndStateAndEmployeeIdAndCompanyId(name,desc,state,employeeId,companyId));
    }

    @Override
    @KafkaListener(topics = "avro-projects-deleted", groupId = "project-group-id")
    public Project consumeKafkaEventProjectDelete(ProjectAvro projectAvro, String topic){
        Project project = Mapper.fromAvroToBean(projectAvro);
        log.info("project to delete :<{}> consumed from topic:<{}>",project,topic);
        return project;
    }

    @Override
    public String deleteProject(String projectId) throws ProjectNotFoundException {
        Project saved = getProject(projectId);
        repository.deleteById(saved.getProjectId());
        return "project <"+saved+"> is deleted";
    }

    @Override
    @KafkaListener(topics = "avro-projects-edited", groupId = "project-group-id")
    public Project consumeKafkaEventProjectUpdate(@Payload ProjectAvro projectAvro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Project project = Mapper.fromAvroToBean(projectAvro);
        log.info("project to update:<{}> consumed from topic:<{}>",project,topic);
        return project;
    }

    @Override
    public Project updateProject(Project payload) {
        ProjectAvro projectAvro = Mapper.fromBeanToAvro(payload);
        Project consumed = consumeKafkaEventProjectUpdate(projectAvro,"avro-projects-edited");
        return Mapper.fromTo(repository.save(Mapper.fromTo(consumed)));
    }

    @Override
    public List<Project> loadProjectsAssignedToEmployee(String employeeId) {
        return mapToBean(repository.findByEmployeeId(employeeId));
    }

    @Override
    public List<Project> loadProjectsOfCompanyById(String companyId) {
        return mapToBean(repository.findByCompanyId(companyId));
    }

    @Override
    public List<Project> getAllProjects() {
        return mapToBean(repository.findAll());
    }

    @Override
    public Company getRemoteCompanyAPI(String companyId) {
        return Mapper.fromTo(companyServiceProxy.loadRemoteCompanyApiById(companyId));
    }

    @Override
    public List<Company> getRemoteCompaniesByName(String companyName) {
        List<CompanyModel> companyModels = companyServiceProxy.loadRemoteCompanyApiByName(companyName);
        return companyModels.stream()
                .map(Mapper::fromTo)
                .toList();
    }

    @Override
    public List<Company> getCompaniesByAgency(String agency) {
        List<CompanyModel> companyModels = companyServiceProxy.loadRemoteCompanyApiByAgency(agency);
        return companyModels.stream()
                .map(Mapper::fromTo)
                .toList();
    }

    @Override
    public Employee getRemoteEmployeeAPI(String employeeId) {
        return Mapper.fromTo(employeeServiceProxy.loadRemoteApiGetEmployee(employeeId));
    }

    @Override
    public List<Employee> getRemoteEmployeesApiByLastname(String lastname) {
      List<EmployeeModel> models =  employeeServiceProxy.loadRemoteApiGetEmployeesByLastname(lastname);
      return models.stream()
              .map(Mapper::fromTo)
              .toList();
    }

    private List<Project> mapToBean(List<ProjectModel> models){
        return models.stream()
                .map(Mapper::fromTo)
                .toList();
    }
}
