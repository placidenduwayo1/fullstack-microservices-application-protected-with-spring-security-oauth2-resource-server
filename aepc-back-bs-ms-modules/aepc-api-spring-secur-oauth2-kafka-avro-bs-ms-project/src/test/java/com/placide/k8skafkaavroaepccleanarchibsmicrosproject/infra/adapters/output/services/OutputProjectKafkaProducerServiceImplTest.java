package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.services;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.avrobeans.ProjectAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.output.mappers.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
class OutputProjectKafkaProducerServiceImplTest {
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:latest"));
    @Mock
    private KafkaTemplate<String, ProjectAvro> kafkaTemplateMock;
    @InjectMocks
    private OutputKafkaProducerProjectServiceImpl underTest;
    private static final String EMPLOYEE_ID = "uuid-e";
    private static final String COMPANY_ID = "uuid-c";
    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        KAFKA_CONTAINER.start();
        String brokers = KAFKA_CONTAINER.getBootstrapServers();
        log.info("list of kafka container brokers: {}", brokers);
        System.setProperty("brokers", brokers);
        Employee remoteEmployee = Employee.builder()
                .employeeId(EMPLOYEE_ID)
                .firstname("Placide")
                .lastname("Nduwayo")
                .email("placide.nduwayo@natan.fr")
                .hireDate("2020-10-27:00:00:00")
                .state("active")
                .role("software-engineer")
                .build();
        Company remoteCompany = Company.builder()
                .companyId(COMPANY_ID)
                .name("Natan")
                .agency("Paris")
                .type("esn")
                .connectedDate("company-connected")
                .build();
        project = new Project.ProjectBuilder()
                .projectId("uuid")
                .name("Guppy")
                .description("outil d'aide au business analyse de la production des besoins techniques")
                .priority(1)
                .state("ongoing")
                .createdDate(Timestamp.from(Instant.now()).toString())
                .employeeId(EMPLOYEE_ID)
                .employee(remoteEmployee)
                .companyId(COMPANY_ID)
                .company(remoteCompany)
                .build();
    }

    @Test
    void produceKafkaEventProjectCreate() {
        //PREPARE
        ProjectAvro projectAvro = Mapper.fromBeanToAvro(project);
        Message<?> message = buildKafkaMessage(projectAvro, "TOPIC1");
        //EXECUTE
        kafkaTemplateMock.send(message);
        ProjectAvro actual = underTest.produceKafkaEventProjectCreate(projectAvro);
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Mockito.verify(kafkaTemplateMock, Mockito.atLeast(1)).send(message);
            Assertions.assertNotNull(actual);
            Assertions.assertEquals(projectAvro.getProjectId(),actual.getProjectId());
            Assertions.assertEquals(projectAvro.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
            Assertions.assertEquals(projectAvro.getEmployee().getFirstname(), actual.getEmployee().getFirstname());
            Assertions.assertEquals(projectAvro.getEmployee().getLastname(), actual.getEmployee().getLastname());
            Assertions.assertEquals(projectAvro.getCompany().getCompanyId(), actual.getCompany().getCompanyId());
            Assertions.assertEquals(projectAvro.getCompany().getName(), actual.getCompany().getName());
            Assertions.assertEquals(projectAvro.getCompany().getAgency(), actual.getCompany().getAgency());
        });
    }

    @Test
    void produceKafkaEventProjectDelete()  {
        //PREPARE
        ProjectAvro projectAvro = Mapper.fromBeanToAvro(project);
        Message<?> message = buildKafkaMessage(projectAvro, "TOPIC2");
        //EXECUTE
        ProjectAvro actual = underTest.produceKafkaEventProjectDelete(projectAvro);
        kafkaTemplateMock.send(message);
        //VERIFY
        Assertions.assertAll("gpe of assertions",
                () -> Mockito.verify(kafkaTemplateMock).send(message),
                ()->Assertions.assertNotNull(actual));
    }

    @Test
    void produceKafkaEventProjectEdit(){
        //PREPARE
        ProjectAvro projectAvro = Mapper.fromBeanToAvro(project);
        Message<?> message = buildKafkaMessage(projectAvro,"TOPIC3");
        //EXECUTE
        ProjectAvro actual = underTest.produceKafkaEventProjectEdit(projectAvro);
        kafkaTemplateMock.send(message);
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Assertions.assertNotNull(actual);
            Mockito.verify(kafkaTemplateMock, Mockito.atLeast(1)).send(message);
        });
    }

    private Message<?> buildKafkaMessage(ProjectAvro payload, String topic) {
        return MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
    }
}