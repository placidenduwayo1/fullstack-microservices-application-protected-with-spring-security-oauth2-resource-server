package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.controller;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.*;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.input.InputProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.input.InputRemoteApiCompanyService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.input.InputRemoteApiEmployeeService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.mappers.Mapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.models.ProjectDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.List;


class ProjectControllerTest {
    @Mock
    private InputProjectService inputProjectService;
    @Mock
    private InputRemoteApiEmployeeService inputRemoteAPIEmployeeService;
    @Mock
    private InputRemoteApiCompanyService inputRemoteAPICompanyService;
    @InjectMocks
    private ProjectController underTest;
    private static final String PROJECT_ID = "project-id";
    private ProjectDto dto;
    private static final String EMPLOYEE_ID = "employee-id";
    private Employee employee;
    private static final String COMPANY_ID = "company-id";
    private Company company;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
       dto = ProjectDto.builder()
                .name("Guppy")
                .description("")
                .priority(1)
                .state("ongoing")
                .employeeId(EMPLOYEE_ID)
                .companyId(COMPANY_ID)
                .build();
        employee = Employee.builder()
                .employeeId(EMPLOYEE_ID)
                .firstname("Placide")
                .lastname("Nduwayo")
                .email("placide.nduwayo@natan.fr")
                .hireDate("2020-10-27:00:00:00")
                .state("active")
                .role("software-engineer")
                .build();
        company = Company.builder()
                .companyId(COMPANY_ID)
                .name("Natan")
                .agency("Paris")
                .type("esn")
                .connectedDate("company-connected")
                .build();
    }

    @Test
    void getWelcome(){
        ResponseEntity<Object> response = underTest.getWelcome();
        Assertions.assertAll("",()->{
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.getStatusCode().value());
        });
    }
    @Test
    void produceConsumeAndSave() throws RemoteCompanyApiException, ProjectPriorityInvalidException, ProjectAlreadyExistsException,
            RemoteEmployeeApiException, ProjectStateInvalidException, ProjectFieldsEmptyException, RemoteEmployeeStateUnauthorizedException {
        //PREPARE
        Project bean = Mapper.fromTo(dto);
        bean.setCompany(company);
        bean.setEmployee(employee);
        //EXECUTE
        Mockito.when(inputRemoteAPIEmployeeService.getRemoteEmployeeAPI(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(inputRemoteAPICompanyService.getRemoteApiCompany(COMPANY_ID)).thenReturn(company);
        Mockito.when(inputProjectService.produceKafkaEventProjectCreate(Mockito.any(ProjectDto.class))).thenReturn(bean);
        List<String> consumedAndSaved = underTest.produceConsumeAndSave(dto);
        Mockito.when(inputProjectService.createProject(Mockito.any(Project.class))).thenReturn(bean);

        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Mockito.verify(inputProjectService, Mockito.atLeast(1)).produceKafkaEventProjectCreate(dto);
            Mockito.verify(inputProjectService, Mockito.atLeast(1)).createProject(bean);
            Assertions.assertNotNull(consumedAndSaved);
            Assertions.assertNotNull(bean.getCompany());
            Assertions.assertNotNull(bean.getEmployee());
        });
    }

    @Test
    void getAllProjects() {
        //PREPARE
        List<Project> beans = List.of(Mapper.fromTo(dto), Mapper.fromTo(dto));
        beans.forEach(bean -> {
            bean.setCompany(company);
            bean.setEmployee(employee);
        });
        //EXECUTE
        Mockito.when(inputProjectService.getAllProjects()).thenReturn(beans);
        List<Project> actuals = underTest.getAllProjects();
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Mockito.verify(inputProjectService, Mockito.atLeast(1)).getAllProjects();
            Assertions.assertEquals(beans, actuals);
            actuals.forEach((var project) -> {
                Assertions.assertNotNull(project.getCompany());
                Assertions.assertNotNull(project.getEmployee());
            });
        });
    }

    @Test
    void getProject() throws ProjectNotFoundException {
        //PREPARE
        Project bean = Mapper.fromTo(dto);
        bean.setEmployee(employee);
        bean.setCompany(company);
        //EXECUTE
        Mockito.when(inputProjectService.getProject(PROJECT_ID)).thenReturn(bean);
        Project actual = underTest.getProject(PROJECT_ID);
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Mockito.verify(inputProjectService, Mockito.atLeast(1)).getProject(PROJECT_ID);
            Assertions.assertEquals("employee-id", actual.getEmployee().getEmployeeId());
            Assertions.assertNotNull(actual.getEmployee());
            Assertions.assertNotNull(actual.getCompany());
            Assertions.assertEquals(bean, actual);
        });
    }

    @Test
    void getProjectsByEmployee() throws RemoteEmployeeApiException {
        //PREPARE
        List<Project> beans = List.of(Mapper.fromTo(dto), Mapper.fromTo(dto));
        beans.forEach(bean -> {
            bean.setCompany(company);
            bean.setEmployee(employee);
        });
        //EXECUTE
        Mockito.when(inputProjectService.loadProjectsAssignedToEmployee(EMPLOYEE_ID)).thenReturn(beans);
        List<Project> actuals = underTest.getProjectsByEmployeeId(EMPLOYEE_ID);
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Assertions.assertEquals(beans, actuals);
            Mockito.verify(inputProjectService, Mockito.atLeast(1)).loadProjectsAssignedToEmployee(EMPLOYEE_ID);
            actuals.forEach((var project) -> {
                Assertions.assertNotNull(project.getCompany());
                Assertions.assertNotNull(project.getEmployee());
            });
        });
    }

    @Test
    void getProjectsByCompany() throws RemoteCompanyApiException {
        //PREPARE
        List<Project> beans = List.of(Mapper.fromTo(dto), Mapper.fromTo(dto));
        beans.forEach(bean -> {
            bean.setCompany(company);
            bean.setEmployee(employee);
        });
        //EXECUTE
        Mockito.when(inputProjectService.loadProjectsOfCompany(COMPANY_ID)).thenReturn(beans);
        List<Project> actuals = underTest.getProjectsByCompany(COMPANY_ID);
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Assertions.assertEquals(beans, actuals);
            Mockito.verify(inputProjectService, Mockito.atLeast(1)).loadProjectsOfCompany(COMPANY_ID);
            actuals.forEach((var project) -> {
                Assertions.assertNotNull(project.getCompany());
                Assertions.assertNotNull(project.getEmployee());
            });
        });
    }

    @Test
    void delete() throws ProjectNotFoundException, RemoteCompanyApiException, RemoteEmployeeApiException, ProjectAssignedRemoteEmployeeApiException,
            ProjectAssignedRemoteCompanyApiException {
        //PREPARE
        Project bean = Mapper.fromTo(dto);
        //EXECUTE
        Mockito.when(inputProjectService.produceKafkaEventProjectDelete(PROJECT_ID)).thenReturn(bean);
        Mockito.when(inputProjectService.deleteProject(bean.getProjectId())).thenReturn("");
        underTest.delete(PROJECT_ID);
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Mockito.verify(inputProjectService, Mockito.atLeast(1)).produceKafkaEventProjectDelete(PROJECT_ID);
            Mockito.verify(inputProjectService, Mockito.atLeast(1)).deleteProject(bean.getProjectId());
        });
    }

    @Test
    void update() throws ProjectNotFoundException, RemoteCompanyApiException, ProjectPriorityInvalidException,
            RemoteEmployeeApiException, ProjectStateInvalidException, ProjectFieldsEmptyException, RemoteEmployeeStateUnauthorizedException {

        //PREPARE
        Project bean = Mapper.fromTo(dto);
        //EXECUTE
        Mockito.when(inputProjectService.getProject(PROJECT_ID)).thenReturn(bean);
        Project actual = underTest.getProject(PROJECT_ID);
        Mockito.when(inputRemoteAPIEmployeeService.getRemoteEmployeeAPI(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(inputRemoteAPICompanyService.getRemoteApiCompany(COMPANY_ID)).thenReturn(company);
        Mockito.when(inputProjectService.produceKafkaEventProjectUpdate(dto,PROJECT_ID)).thenReturn(bean);
        Mockito.when(inputProjectService.updateProject(actual)).thenReturn(new Project.ProjectBuilder().build());
        List<String> consumedAndSaved = underTest.update(PROJECT_ID, dto);
        //VERIFY
        Assertions.assertAll("assertions",()->{
            Mockito.verify(inputProjectService, Mockito.atLeast(1)).getProject(PROJECT_ID);
            Mockito.verify(inputProjectService, Mockito.atLeast(1)).produceKafkaEventProjectUpdate(dto, PROJECT_ID);
            Mockito.verify(inputProjectService, Mockito.atLeast(1)).updateProject(actual);
            Assertions.assertNotNull(consumedAndSaved);
        });
    }
}