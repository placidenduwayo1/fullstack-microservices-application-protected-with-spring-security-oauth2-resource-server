package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.usecase;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.avrobeans.ProjectAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.*;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output.OutputKafkaProducerProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output.OutputProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output.OutputRemoteApiCompanyService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output.OutputRemoteApiEmployeeService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.mappers.Mapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.models.ProjectDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Slf4j
class UseCaseTest {
    @Mock
    private OutputKafkaProducerProjectService kafkaProducerService;
    @Mock
    private OutputProjectService outputProjectService;
    @Mock
    private OutputRemoteApiEmployeeService outputRemoteAPIEmployeeService;
    @Mock
    private OutputRemoteApiCompanyService outputRemoteAPICompanyService;
    @InjectMocks
    private UseCase underTest;
    private static final String PROJECT_ID = "project-id";
    private ProjectDto projectDto;
    private Project project;
    private static final String EMPLOYEE_ID = "employee-id";
    private Employee employee;
    private static final String COMPANY_ID = "company-id";
    private Company company;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectDto = ProjectDto.builder()
                .name("Guppy")
                .description("outil d'aide au business analyst (BA) à la rédaction des us")
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
        project = Mapper.fromTo(projectDto);
        project.setProjectId(PROJECT_ID);
        project.setCreatedDate(Timestamp.from(Instant.now()).toString());
        project.setEmployeeId(EMPLOYEE_ID);
        project.setEmployee(employee);
        project.setCompanyId(COMPANY_ID);
        project.setCompany(company);
    }

    @Test
    void produceKafkaEventProjectCreate() throws RemoteCompanyApiException, ProjectPriorityInvalidException, ProjectAlreadyExistsException,
            RemoteEmployeeApiException, ProjectStateInvalidException, ProjectFieldsEmptyException, RemoteEmployeeStateUnauthorizedException {
        //PREPARE
        ProjectAvro projectAvro = Mapper.fromBeanToAvro(project);
        //EXECUTE
        Mockito.when(outputRemoteAPIEmployeeService.getRemoteEmployeeAPI(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(outputRemoteAPICompanyService.getRemoteCompanyAPI(COMPANY_ID)).thenReturn(company);
        Mockito.when(kafkaProducerService.produceKafkaEventProjectCreate(Mockito.any(ProjectAvro.class))).thenReturn(projectAvro);
        Project actual = underTest.produceKafkaEventProjectCreate(projectDto);
        //VERIFY
        Assertions.assertAll("gpe of assertions", () -> {
            Mockito.verify(outputRemoteAPIEmployeeService, Mockito.atLeast(1)).getRemoteEmployeeAPI(EMPLOYEE_ID);
            Mockito.verify(outputRemoteAPICompanyService, Mockito.atLeast(1)).getRemoteCompanyAPI(COMPANY_ID);
            Mockito.verify(kafkaProducerService, Mockito.atLeast(1)).produceKafkaEventProjectCreate(Mockito.any());
            Assertions.assertNotNull(actual);
            Assertions.assertEquals(projectAvro.getProjectId(),actual.getProjectId());
            Assertions.assertEquals(projectAvro.getCompany().getCompanyId(), actual.getCompany().getCompanyId());
            Assertions.assertEquals(projectAvro.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
        });
    }

    @Test
    void createProject() throws RemoteCompanyApiException, RemoteEmployeeApiException {
        //PREPARE
        Project bean = Mapper.fromTo(projectDto);
        //EXECUTE
        Mockito.when(outputRemoteAPIEmployeeService.getRemoteEmployeeAPI(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(outputRemoteAPICompanyService.getRemoteCompanyAPI(COMPANY_ID)).thenReturn(company);
        Mockito.when(outputProjectService.saveProject(Mockito.any(Project.class))).thenReturn(bean);
        Project saved = underTest.createProject(bean);
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Mockito.verify(outputRemoteAPIEmployeeService, Mockito.atLeast(1)).getRemoteEmployeeAPI(EMPLOYEE_ID);
            Mockito.verify(outputRemoteAPICompanyService, Mockito.atLeast(1)).getRemoteCompanyAPI(COMPANY_ID);
            Mockito.verify(outputProjectService, Mockito.atLeast(1)).saveProject(bean);
            Assertions.assertEquals(bean, saved);
        });
    }

    @Test
    void getProject() throws ProjectNotFoundException {
        //PREPARE
        Project bean = Mapper.fromTo(projectDto);
        //EXECUTE
        Mockito.when(outputProjectService.getProject(PROJECT_ID)).thenReturn(bean);
        Project actual = underTest.getProject(PROJECT_ID);
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Mockito.verify(outputProjectService, Mockito.atLeast(1)).getProject(PROJECT_ID);
            Assertions.assertEquals(bean, actual);
        });
    }

    @Test
    void loadProjectByInfo() {
        //PREPARE
        String name = "Guppy";
        String description = "description";
        String state = "ongoing";
        List<Project> projects = List.of(Mapper.fromTo(projectDto));
        //EXECUTE
        Mockito.when(outputProjectService.loadProjectByInfo(name, description, state, EMPLOYEE_ID, COMPANY_ID)).thenReturn(projects);
        List<Project> lists = underTest.loadProjectByInfo(name, description, state, EMPLOYEE_ID, COMPANY_ID);
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Mockito.verify(outputProjectService, Mockito.atLeast(1))
                    .loadProjectByInfo(name, description, state, EMPLOYEE_ID, COMPANY_ID);
            Assertions.assertEquals(projects.size(), lists.size());
        });
    }

    @Test
    void produceKafkaEventProjectDelete() throws ProjectNotFoundException, RemoteCompanyApiException, RemoteEmployeeApiException,
            ProjectAssignedRemoteEmployeeApiException, ProjectAssignedRemoteCompanyApiException {
        //PREPARE
        ProjectAvro projectAvro = Mapper.fromBeanToAvro(project);
        employee.setEmployeeId(ExceptionMsg.REMOTE_EMPLOYEE_API_EXCEPTION);
        company.setCompanyId(ExceptionMsg.REMOTE_COMPANY_API_EXCEPTION);
        //EXECUTE
        Mockito.when(outputProjectService.getProject(PROJECT_ID)).thenReturn(project);
        Mockito.when(outputRemoteAPIEmployeeService.getRemoteEmployeeAPI(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(outputRemoteAPICompanyService.getRemoteCompanyAPI(COMPANY_ID)).thenReturn(company);
        Mockito.when(kafkaProducerService.produceKafkaEventProjectDelete(projectAvro)).thenReturn(projectAvro);
        Project actual = underTest.produceKafkaEventProjectDelete(PROJECT_ID);
        //VERIFY
        Assertions.assertAll("gpe of assertions", () -> {
            Mockito.verify(outputProjectService, Mockito.atLeast(1))
                    .getProject(PROJECT_ID);
            Mockito.verify(kafkaProducerService, Mockito.atLeast(1))
                    .produceKafkaEventProjectDelete(projectAvro);
            Assertions.assertEquals(projectAvro.getProjectId(),actual.getProjectId());
            Assertions.assertEquals(projectAvro.getCompany().getCompanyId(), actual.getCompany().getCompanyId());
            Assertions.assertEquals(projectAvro.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
            Mockito.verify(outputRemoteAPIEmployeeService, Mockito.atLeast(1))
                    .getRemoteEmployeeAPI(EMPLOYEE_ID);
            Mockito.verify(outputRemoteAPICompanyService, Mockito.atLeast(1))
                    .getRemoteCompanyAPI(COMPANY_ID);
        });
    }

    @Test
    void deleteProject() throws ProjectNotFoundException {
        //PREPARE
        Project bean = Mapper.fromTo(projectDto);
        //EXECUTE
        Mockito.when(outputProjectService.getProject(PROJECT_ID)).thenReturn(bean);
        Project obtained = underTest.getProject(PROJECT_ID);
        Mockito.when(outputProjectService.deleteProject(obtained.getProjectId())).thenReturn("");
        String msg = underTest.deleteProject(PROJECT_ID);
        //VERIFY
        Assertions.assertAll("gpe of assertions",
                () -> Mockito.verify(outputProjectService, Mockito.atLeast(1))
                        .deleteProject(obtained.getProjectId()),
                () -> Assertions.assertEquals("Project" + obtained + "successfully deleted", msg),
                () -> Assertions.assertEquals(bean, obtained));
    }


    @Test
    void produceKafkaEventProjectUpdate() throws ProjectNotFoundException, RemoteCompanyApiException, RemoteEmployeeApiException,
            ProjectPriorityInvalidException, ProjectStateInvalidException, ProjectFieldsEmptyException, RemoteEmployeeStateUnauthorizedException {
        //PREPARE
        ProjectAvro projectAvro = Mapper.fromBeanToAvro(project);
        log.info("{}",projectAvro.toString());
        //EXECUTE
        Mockito.when(outputRemoteAPIEmployeeService.getRemoteEmployeeAPI(EMPLOYEE_ID))
                .thenReturn(employee);
        Mockito.when(outputRemoteAPICompanyService.getRemoteCompanyAPI(COMPANY_ID))
                .thenReturn(company);
        Mockito.when(outputProjectService.getProject(PROJECT_ID)).thenReturn(project);
        Mockito.when(kafkaProducerService.produceKafkaEventProjectEdit(Mockito.any(ProjectAvro.class)))
                .thenReturn(projectAvro);
        Project actual = underTest.produceKafkaEventProjectUpdate(projectDto, PROJECT_ID);
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Mockito.verify(outputRemoteAPIEmployeeService, Mockito.atLeast(1))
                    .getRemoteEmployeeAPI(projectDto.getEmployeeId());
            Mockito.verify(outputRemoteAPICompanyService, Mockito.atLeast(1))
                    .getRemoteCompanyAPI(projectDto.getCompanyId());
            Mockito.verify(outputProjectService, Mockito.atLeast(1)).getProject(PROJECT_ID);
            Mockito.verify(kafkaProducerService, Mockito.atLeast(1))
                    .produceKafkaEventProjectEdit(Mockito.any(ProjectAvro.class));
            Assertions.assertNotNull(actual);
        });
    }

    @Test
    void updateProject() throws RemoteCompanyApiException, RemoteEmployeeApiException {
        //PREPARE
        Project bean = Mapper.fromTo(projectDto);
        //EXECUTE
        Mockito.when(outputRemoteAPIEmployeeService.getRemoteEmployeeAPI(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(outputRemoteAPICompanyService.getRemoteCompanyAPI(COMPANY_ID)).thenReturn(company);
        Mockito.when(outputProjectService.updateProject(bean)).thenReturn(bean);
        Project updated = underTest.updateProject(bean);
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Mockito.verify(outputRemoteAPIEmployeeService,Mockito.atLeast(1)).getRemoteEmployeeAPI(EMPLOYEE_ID);
            Mockito.verify(outputRemoteAPICompanyService, Mockito.atLeast(1)).getRemoteCompanyAPI(COMPANY_ID);
            Mockito.verify(outputProjectService, Mockito.atLeast(1)).updateProject(bean);
            Assertions.assertNotNull(updated);
        });
    }

    @Test
    void loadProjectsAssignedToEmployee() throws RemoteEmployeeApiException, RemoteCompanyApiException {
        //PREPARE
        List<Project> beans = List.of(Mapper.fromTo(projectDto));
        //EXECUTE
        Mockito.when(outputRemoteAPIEmployeeService.getRemoteEmployeeAPI(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(outputRemoteAPICompanyService.getRemoteCompanyAPI(COMPANY_ID)).thenReturn(company);
        Employee obtained = underTest.getRemoteEmployeeAPI(EMPLOYEE_ID);
        Mockito.when(outputProjectService.loadProjectsAssignedToEmployee(EMPLOYEE_ID)).thenReturn(beans);
        List<Project> projects = underTest.loadProjectsAssignedToEmployee(EMPLOYEE_ID);
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Mockito.verify(outputRemoteAPIEmployeeService, Mockito.atLeast(1)).getRemoteEmployeeAPI(EMPLOYEE_ID);
            Mockito.verify(outputRemoteAPICompanyService, Mockito.atLeast(1)).getRemoteCompanyAPI(COMPANY_ID);
            Mockito.verify(outputProjectService, Mockito.atLeast(1)).loadProjectsAssignedToEmployee(EMPLOYEE_ID);
            Assertions.assertEquals(beans.get(0), projects.get(0));
            Assertions.assertEquals(beans.size(), projects.size());
            Assertions.assertEquals(employee, obtained);
        });
    }

    @Test
    void loadProjectsOfCompany() throws RemoteCompanyApiException, RemoteEmployeeApiException {
        //PREPARE
        List<Project> beans = List.of(Mapper.fromTo(projectDto));
        //EXECUTE
        Mockito.when(outputRemoteAPIEmployeeService.getRemoteEmployeeAPI(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(outputRemoteAPICompanyService.getRemoteCompanyAPI(COMPANY_ID)).thenReturn(company);
        Company actual = underTest.getRemoteApiCompany(COMPANY_ID);
        Mockito.when(outputProjectService.loadProjectsOfCompanyById(actual.getCompanyId())).thenReturn(beans);
        List<Project> actuals = underTest.loadProjectsOfCompany(COMPANY_ID);
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Mockito.verify(outputRemoteAPIEmployeeService, Mockito.atLeast(1)).getRemoteEmployeeAPI(EMPLOYEE_ID);
            Mockito.verify(outputRemoteAPICompanyService, Mockito.atLeast(1)).getRemoteCompanyAPI(COMPANY_ID);
            Mockito.verify(outputProjectService, Mockito.atLeast(1)).loadProjectsOfCompanyById(actual.getCompanyId());
            Assertions.assertEquals(company, actual);
            Assertions.assertFalse(actuals.isEmpty());
        });
    }

    @Test
    void getAllProjects() throws RemoteEmployeeApiException, RemoteCompanyApiException {
        //PREPARE
        List<Project> beans = List.of(Mapper.fromTo(projectDto));
        //EXECUTE
        Mockito.when(outputRemoteAPIEmployeeService.getRemoteEmployeeAPI(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(outputRemoteAPICompanyService.getRemoteCompanyAPI(COMPANY_ID)).thenReturn(company);
        Mockito.when(outputProjectService.getAllProjects()).thenReturn(beans);
        List<Project> actuals = underTest.getAllProjects();
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Mockito.verify(outputProjectService, Mockito.atLeast(1)).getAllProjects();
            Assertions.assertEquals(beans.size(), actuals.size());
            Mockito.verify(outputRemoteAPIEmployeeService, Mockito.atLeast(1)).getRemoteEmployeeAPI(EMPLOYEE_ID);
            Mockito.verify(outputRemoteAPICompanyService, Mockito.atLeast(1)).getRemoteCompanyAPI(COMPANY_ID);
        });
    }

    @Test
    void getRemoteCompanyAPI() throws RemoteCompanyApiException {
        //PREPARE
        //EXECUTE
        Mockito.when(outputRemoteAPICompanyService.getRemoteCompanyAPI(COMPANY_ID)).thenReturn(company);
        Company actual = underTest.getRemoteApiCompany(COMPANY_ID);
        //VERIFY
        Assertions.assertAll("gpe of assertions",
                () -> Mockito.verify(outputRemoteAPICompanyService, Mockito.atLeast(1)).getRemoteCompanyAPI(COMPANY_ID),
                () -> Assertions.assertEquals(company, actual));
    }

    @Test
    void getRemoteEmployeeAPI() throws RemoteEmployeeApiException {
        //PREPARE
        //EXECUTE
        Mockito.when(outputRemoteAPIEmployeeService.getRemoteEmployeeAPI(EMPLOYEE_ID)).thenReturn(employee);
        Employee actual = underTest.getRemoteEmployeeAPI(EMPLOYEE_ID);
        //VERIFY
        Assertions.assertAll("gpe of assertions",
                () -> Mockito.verify(outputRemoteAPIEmployeeService, Mockito.atLeast(1))
                        .getRemoteEmployeeAPI(EMPLOYEE_ID),
                () -> Assertions.assertEquals(employee, actual));
    }
}