package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.service;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.EmployeeAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.address.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions.RemoteApiAddressNotLoadedException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.models.AddressModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.proxies.AddressServiceProxy;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.mapper.AddressMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.mapper.EmployeeMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.models.EmployeeModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.repository.EmployeeRepository;
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
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
import org.testcontainers.utility.DockerImageName;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
class OutputKafkaProducerEmployeeServiceImplTest {
    private final KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.
            parse("confluentinc/cp-kafka:latest"));
    @Mock
    private KafkaTemplate<String, EmployeeAvro> employeeAvroKafkaTemplate;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private AddressServiceProxy addressServiceProxy;
    @InjectMocks
    private OutputKafkaProducerEmployeeServiceImpl underTest;
    private static final List<String> TOPICS = List
            .of("avro-employees-created", "avro-employees-deleted", "avro-employees-edited");
    private Employee employee;
    private Address address;
    private EmployeeAvro employeeAvro;
    private static final String ADDRESS_ID = "1L";

    @BeforeEach
    void setUp() {
        kafkaContainer.start();
        MockitoAnnotations.openMocks(this);
        String bootstrapServers = kafkaContainer.getBootstrapServers();
        log.info("list of kafka container brokers: {}", bootstrapServers);
        System.setProperty("kafka.bootstrapAddress", bootstrapServers);

        address = Address.builder()
                .addressId(ADDRESS_ID)
                .num(184)
                .street("Avenue de Liège")
                .pb(5930)
                .city("Valenciennes")
                .country("France")
                .build();

        employee = new Employee.EmployeeBuilder()
                .employeeId(UUID.randomUUID().toString())
                .firstname("Placide")
                .lastname("Nduwayo")
                .email("placide.nduwayo@domain.com")
                .hireDate(Timestamp.from(Instant.now()).toString())
                .state("active")
                .type("software-engineer")
                .addressId(ADDRESS_ID)
                .address(address)
                .build();
        employeeAvro = EmployeeMapper.fromBeanToAvro(employee);
    }

    @Test
    void produceKafkaEventEmployeeCreate() {
        //PREPARE
        Message<?> message = buildKafkaMessage(employeeAvro, TOPICS.get(0));
        //EXECUTE
        employeeAvroKafkaTemplate.send(message);
        EmployeeAvro actual = underTest.produceKafkaEventEmployeeCreate(employeeAvro);
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Mockito.verify(employeeAvroKafkaTemplate).send(message);
            Assertions.assertNotNull(actual);
        });
    }

    @Test
    void produceKafkaEventEmployeeDelete() throws RemoteApiAddressNotLoadedException {
        //PREPARE
        Message<?> message = buildKafkaMessage(employeeAvro, TOPICS.get(1));
        AddressModel addressModel = AddressMapper.toModel(address);
        //EXECUTE
        Mockito.when(addressServiceProxy.loadRemoteAddressById(employee.getAddressId())).thenReturn(addressModel);
        EmployeeAvro actual = underTest.produceKafkaEventEmployeeDelete(employeeAvro);
        employeeAvroKafkaTemplate.send(message);
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Mockito.verify(employeeAvroKafkaTemplate, Mockito.atLeast(1)).send(message);
            Assertions.assertNotNull(actual);
        });
    }

    @Test
    void produceKafkaEventEmployeeEdit() throws RemoteApiAddressNotLoadedException {
        //PREPARE
        String employeeId = "uuid-1";
        Employee bean = new Employee.EmployeeBuilder()
                .employeeId(UUID.randomUUID().toString())
                .firstname("Placide")
                .lastname("Nduwayo")
                .email("placide.nduwayo@domain.com")
                .hireDate(Timestamp.from(Instant.now()).toString())
                .state("active")
                .type("software-engineer")
                .addressId(ADDRESS_ID)
                .address(address)
                .build();
        EmployeeAvro avro = EmployeeMapper.fromBeanToAvro(bean);
        Message<?> message = buildKafkaMessage(avro, TOPICS.get(2));
        AddressModel addressModel = AddressModel.builder()
                .addressId(ADDRESS_ID)
                .num(184)
                .street("Avenue de Liège")
                .pb(5930)
                .city("Valenciennes")
                .country("France")
                .build();
        EmployeeModel employeeModel = EmployeeMapper.toModel(bean);
        //EXECUTE
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeModel));
        Mockito.when(addressServiceProxy.loadRemoteAddressById(employee.getAddressId())).thenReturn(addressModel);
        EmployeeAvro actual = underTest.produceKafkaEventEmployeeEdit(avro);
        employeeAvroKafkaTemplate.send(message);
        //VERIFY
        Assertions.assertAll("gpe of assertions",()->{
            Mockito.verify(employeeAvroKafkaTemplate).send(message);
            Assertions.assertNotNull(actual);
            Assertions.assertEquals(actual.toString(),avro.toString());
        });
    }
    private Message<?> buildKafkaMessage(EmployeeAvro payload, String topic) {
        return MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
    }
}