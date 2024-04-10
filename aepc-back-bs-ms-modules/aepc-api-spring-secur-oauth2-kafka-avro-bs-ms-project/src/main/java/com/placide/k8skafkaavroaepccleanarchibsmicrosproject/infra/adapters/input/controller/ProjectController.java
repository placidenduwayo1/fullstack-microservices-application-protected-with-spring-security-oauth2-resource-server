package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.controller;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.*;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.input.InputProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.models.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api-project")
public class ProjectController {
    private final InputProjectService inputProjectService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getWelcome() {
        return new ResponseEntity<>("welcome to business-microservice-project managing projects and employees, companies assigned",
                HttpStatus.OK);
    }

    @PostMapping(value = "/projects")
    public List<String> produceConsumeAndSave(@RequestBody ProjectDto dto) throws RemoteCompanyApiException,
            ProjectPriorityInvalidException, ProjectAlreadyExistsException, RemoteEmployeeApiException, ProjectStateInvalidException,
            ProjectFieldsEmptyException, RemoteEmployeeStateUnauthorizedException {
        Project consumed = inputProjectService.produceKafkaEventProjectCreate(dto);
        Project saved = inputProjectService.createProject(consumed);
        return List.of("consumed: " + consumed, "saved: " + saved);
    }

    @GetMapping(value = "/projects")
    public List<Project> getAllProjects() {
        return inputProjectService.getAllProjects();
    }

    @GetMapping(value = "/projects/{id}")
    public Project getProject(@PathVariable(name = "id") String id) throws ProjectNotFoundException {
        return inputProjectService.getProject(id);
    }

    @GetMapping(value = "/projects/employees/id/{employeeId}")
    public List<Project> getProjectsByEmployeeId(@PathVariable(name = "employeeId") String employeeId) throws RemoteEmployeeApiException {
        return inputProjectService.loadProjectsAssignedToEmployee(employeeId);
    }

    @GetMapping(value = "/projects/companies/id/{companyId}")
    public List<Project> getProjectsByCompany(@PathVariable(name = "companyId") String companyId) throws RemoteCompanyApiException {
        return inputProjectService.loadProjectsOfCompany(companyId);
    }

    @DeleteMapping(value = "/projects/{id}")
    public void delete(@PathVariable(name = "id") String id) throws ProjectNotFoundException,
            RemoteCompanyApiException, RemoteEmployeeApiException, ProjectAssignedRemoteEmployeeApiException, ProjectAssignedRemoteCompanyApiException {
        Project consumed = inputProjectService.produceKafkaEventProjectDelete(id);
        inputProjectService.deleteProject(consumed.getProjectId());
    }

    @PutMapping(value = "/projects/{id}")
    public List<String> update(@PathVariable(name = "id") String id, @RequestBody ProjectDto dto) throws ProjectNotFoundException,
            RemoteCompanyApiException, ProjectPriorityInvalidException, RemoteEmployeeApiException, ProjectStateInvalidException,
            ProjectFieldsEmptyException, RemoteEmployeeStateUnauthorizedException {
        Project consumed = inputProjectService.produceKafkaEventProjectUpdate(dto, id);
        Project saved = inputProjectService.updateProject(consumed);
        return List.of("consumed: " + consumed, "saved: " + saved);
    }

    @GetMapping(value = "/projects/companies/name/{companyName}")
    public List<Project> getProjectsByCompanyName(@PathVariable(name = "companyName") String companyName) throws RemoteCompanyApiException {
        return inputProjectService.loadProjectsOfCompanyName(companyName);
    }

    @GetMapping(value = "/projects/companies/agency/{agency}")
    public List<Project> getProjectByCompaniesAgency(@PathVariable(name = "agency") String agency) throws RemoteCompanyApiException {
        return inputProjectService.getProjectsByCompanyAgency(agency);
    }

    @GetMapping(value = "/projects/employees/lastname/{lastname}")
    public List<Project> getProjectsByEmployeeLastname(@PathVariable(name = "lastname") String lastname) throws RemoteEmployeeApiException {
        return inputProjectService.loadProjectsAssignedToEmployeeLastname(lastname);
    }

}
