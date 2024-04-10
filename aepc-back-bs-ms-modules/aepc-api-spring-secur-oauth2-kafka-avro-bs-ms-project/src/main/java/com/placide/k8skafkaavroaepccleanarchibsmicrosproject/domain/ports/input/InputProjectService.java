package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.input;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.*;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.models.ProjectDto;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.RemoteCompanyApiException;
import java.util.List;
import java.util.Optional;

public interface InputProjectService {
    Project produceKafkaEventProjectCreate(ProjectDto dto) throws ProjectAlreadyExistsException,
            ProjectPriorityInvalidException, ProjectStateInvalidException, RemoteEmployeeApiException, RemoteCompanyApiException,
            ProjectFieldsEmptyException, RemoteEmployeeStateUnauthorizedException;
    Project createProject(Project project) throws RemoteCompanyApiException, RemoteEmployeeApiException;
    Project getProject(String projectId) throws ProjectNotFoundException;
    List<Project> loadProjectByInfo(String name, String desc, String state,String employeeId, String companyId);
    Project produceKafkaEventProjectDelete(String projectId) throws ProjectNotFoundException, RemoteEmployeeApiException, RemoteCompanyApiException, ProjectAssignedRemoteEmployeeApiException, ProjectAssignedRemoteCompanyApiException;
    String deleteProject(String projectId) throws ProjectNotFoundException;
    Project produceKafkaEventProjectUpdate(ProjectDto payload, String projectId) throws ProjectNotFoundException,
            ProjectPriorityInvalidException, ProjectStateInvalidException, RemoteEmployeeApiException, RemoteCompanyApiException,
            ProjectFieldsEmptyException, RemoteEmployeeStateUnauthorizedException;
    Project updateProject (Project payload) throws RemoteCompanyApiException, RemoteEmployeeApiException;
    List<Project> loadProjectsAssignedToEmployee(String employeeId) throws RemoteEmployeeApiException;
    List<Project> loadProjectsOfCompany(String companyId) throws RemoteCompanyApiException;

    List<Project> getAllProjects();
    List<Project> loadProjectsOfCompanyName(String companyName) throws RemoteCompanyApiException;
    List<Project> getProjectsByCompanyAgency(String companyAgency) throws RemoteCompanyApiException;
    List<Project> loadProjectsAssignedToEmployeeLastname(String employeeLastname) throws RemoteEmployeeApiException;
}
