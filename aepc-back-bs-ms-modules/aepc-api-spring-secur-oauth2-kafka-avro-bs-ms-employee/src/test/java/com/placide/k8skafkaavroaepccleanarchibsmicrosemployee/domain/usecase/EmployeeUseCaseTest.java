package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.usecase;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.EmployeeAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.address.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions.*;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.output.OutputEmployeeService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.output.OutputKafkaProducerEmployeeService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.output.RemoteOutputAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.output.RemoteOutputProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.mapper.EmployeeMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.models.EmployeeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

class EmployeeUseCaseTest {
    @Mock
    private OutputKafkaProducerEmployeeService kafkaProducerMock;
    @Mock
    private OutputEmployeeService employeeServiceMock;
    @Mock
    private RemoteOutputAddressService remoteOutputAddressService;
    @Mock
    private RemoteOutputProjectService remoteOutputProjectService;
    @InjectMocks
    private EmployeeUseCase underTest;
    private static final String FIRSTNAME = "Placide";
    private static final String LASTNAME = "Nduwayo";
    private static final String STATE = "active";
    private static final String TYPE = "software-engineer";
    private Address remoteAddress;
    private static final String ADDRESS_ID = "1L";
    private EmployeeDto dto;
    private static final String EMPLOYEE_ID = "uuid-employee";
    private Employee bean;
    private EmployeeAvro avro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        remoteAddress = Address.builder()
                .addressId(ADDRESS_ID)
                .num(184)
                .street("Avenue de Liège")
                .pb(5930)
                .city("Valenciennes")
                .country("France")
                .build();
        dto = EmployeeDto.builder()
                .firstname(FIRSTNAME)
                .lastname(LASTNAME)
                .state(STATE)
                .role(TYPE)
                .addressId(ADDRESS_ID)
                .build();
        bean = EmployeeMapper.fromDto(dto);
        bean.setEmployeeId(EMPLOYEE_ID);
        bean.setEmail(Validator.setEmail(FIRSTNAME, LASTNAME));
        bean.setHireDate(Timestamp.from(Instant.now()).toString());
        bean.setAddress(remoteAddress);
        avro = EmployeeMapper.fromBeanToAvro(bean);
    }

    @Test
    void produceKafkaEventEmployeeCreate() throws EmployeeTypeInvalidException, EmployeeEmptyFieldsException,
            EmployeeStateInvalidException, RemoteApiAddressNotLoadedException, EmployeeAlreadyExistsException {
        //PREPARE
        //EXECUTE
        Mockito.when(remoteOutputAddressService.getRemoteAddressById(ADDRESS_ID)).thenReturn(remoteAddress);
        Mockito.when(kafkaProducerMock.produceKafkaEventEmployeeCreate(Mockito.any(EmployeeAvro.class))).thenReturn(avro);
        Employee actual = underTest.produceKafkaEventEmployeeCreate(dto);
        //VERIFY
        Assertions.assertAll("grp of assertions", () -> {
            Mockito.verify(kafkaProducerMock, Mockito.atLeast(1)).produceKafkaEventEmployeeCreate(Mockito.any());
            Mockito.verify(remoteOutputAddressService, Mockito.atLeast(1)).getRemoteAddressById(ADDRESS_ID);
            Assertions.assertNotNull(actual);
            Assertions.assertEquals(actual.getEmployeeId(), avro.getEmployeeId());
            Assertions.assertEquals(actual.getAddress().getAddressId(), avro.getAddress().getAddressId());
        });
    }

    @Test
    void createEmployee() throws RemoteApiAddressNotLoadedException {
        //PREPARE
        //EXECUTE
        Mockito.when(remoteOutputAddressService.getRemoteAddressById(ADDRESS_ID)).thenReturn(remoteAddress);
        Mockito.when(employeeServiceMock.saveEmployee(bean)).thenReturn(bean);
        Employee actual = underTest.createEmployee(bean);
        //VERIFY
        Assertions.assertAll("gpe of assertions", () -> {
            Mockito.verify(remoteOutputAddressService, Mockito.atLeast(1)).getRemoteAddressById(ADDRESS_ID);
            Mockito.verify(employeeServiceMock, Mockito.atLeast(1)).saveEmployee(bean);
            Assertions.assertNotNull(actual);
            Assertions.assertEquals(bean, actual);
            Assertions.assertNotNull(actual.getAddress());
        });
    }

    @Test
    void produceKafkaEventEmployeeDelete() throws EmployeeNotFoundException, RemoteApiAddressNotLoadedException,
            EmployeeAlreadyAssignedProjectException {
        //PREPARE
        String employeeId = "uuid";
        //EXECUTE
        Mockito.when(remoteOutputAddressService.getRemoteAddressById(ADDRESS_ID)).thenReturn(remoteAddress);
        Mockito.when(remoteOutputProjectService.loadRemoteProjectsAssignedToEmployee(employeeId))
                .thenReturn(Collections.emptyList());
        Mockito.when(employeeServiceMock.getEmployeeById(employeeId)).thenReturn(bean);
        Employee actual = underTest.getEmployeeById(employeeId);
        Mockito.when(kafkaProducerMock.produceKafkaEventEmployeeDelete(avro)).thenReturn(avro);
        Employee produced = underTest.produceKafkaEventEmployeeDelete(employeeId);
        //VERIFY
        Assertions.assertAll("gpe of assertions", () -> {
            Mockito.verify(employeeServiceMock, Mockito.atLeast(1)).getEmployeeById(employeeId);
            Mockito.verify(kafkaProducerMock, Mockito.atLeast(1)).produceKafkaEventEmployeeDelete(avro);
            Assertions.assertEquals(actual.toString(), produced.toString());
        });
    }

    @Test
    void deleteEmployee() throws EmployeeNotFoundException, RemoteApiAddressNotLoadedException {
        //PREPARE
        String id = "uuid";
        //EXECUTE
        Mockito.when(remoteOutputAddressService.getRemoteAddressById(ADDRESS_ID)).thenReturn(remoteAddress);
        Mockito.when(employeeServiceMock.getEmployeeById(id)).thenReturn(bean);
        Employee employee = underTest.getEmployeeById(id);
        Mockito.when(employeeServiceMock.deleteEmployee(employee.getEmployeeId())).thenReturn("");
        String msg = underTest.deleteEmployee(id);
        //VERIFY
        Assertions.assertAll("gpe of assertions", () -> {
            Mockito.verify(employeeServiceMock, Mockito.atLeast(1)).deleteEmployee(employee.getEmployeeId());
            Mockito.verify(employeeServiceMock, Mockito.atLeast(1)).getEmployeeById(id);
            Assertions.assertNotNull(employee);
            Assertions.assertEquals("Employee" + employee + "successfully deleted", msg);
            Mockito.verify(remoteOutputAddressService, Mockito.atLeast(1)).getRemoteAddressById(ADDRESS_ID);
        });
    }

    @Test
    void produceKafkaEventEmployeeEdit() throws EmployeeNotFoundException, RemoteApiAddressNotLoadedException, EmployeeTypeInvalidException, EmployeeEmptyFieldsException, EmployeeStateInvalidException {
        //PREPARE
        String employeeId = "uuid";
        //EXECUTE
        Mockito.when(remoteOutputAddressService.getRemoteAddressById(ADDRESS_ID)).thenReturn(remoteAddress);
        Mockito.when(employeeServiceMock.getEmployeeById(employeeId)).thenReturn(bean);
        Mockito.when(kafkaProducerMock.produceKafkaEventEmployeeEdit(avro)).thenReturn(avro);
        Employee actual = underTest.produceKafkaEventEmployeeEdit(dto, employeeId);
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Mockito.verify(remoteOutputAddressService, Mockito.atLeast(1)).getRemoteAddressById(ADDRESS_ID);
            Mockito.verify(kafkaProducerMock, Mockito.atLeast(1)).produceKafkaEventEmployeeEdit(avro);
            Mockito.verify(employeeServiceMock).getEmployeeById(employeeId);
            Assertions.assertNotNull(actual);
            Assertions.assertEquals(actual.toString(), bean.toString());
        });
    }

    @Test
    void editEmployee() throws RemoteApiAddressNotLoadedException {
        //PREPARE
        Employee updated = bean;
        updated.setAddress(Address.builder()
                .addressId("2L")
                .num(44)
                .street("Rue Notre Dame des Victoires")
                .pb(75002)
                .city("Paris")
                .country("France")
                .build());
        //EXECUTE
        Mockito.when(remoteOutputAddressService.getRemoteAddressById(ADDRESS_ID)).thenReturn(remoteAddress);
        Mockito.when(employeeServiceMock.editEmployee(bean)).thenReturn(updated);
        Employee actual = underTest.editEmployee(bean);
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Mockito.verify(employeeServiceMock, Mockito.atLeast(1)).editEmployee(bean);
            Assertions.assertNotNull(actual);
            Assertions.assertEquals(updated, actual);
            Mockito.verify(remoteOutputAddressService, Mockito.atLeast(1)).getRemoteAddressById(ADDRESS_ID);
        });
    }

    @Test
    void loadEmployeesByRemoteAddress() throws RemoteApiAddressNotLoadedException {
        //PREPARE
        List<Employee> beans = List.of(bean, bean, bean);
        //EXECUTE
        Mockito.when(employeeServiceMock.loadEmployeesByRemoteAddress(ADDRESS_ID)).thenReturn(beans);
        Mockito.when(remoteOutputAddressService.getRemoteAddressById(ADDRESS_ID)).thenReturn(remoteAddress);
        List<Employee> actuals = underTest.loadEmployeesByRemoteAddress(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Mockito.verify(employeeServiceMock, Mockito.atLeast(1)).loadEmployeesByRemoteAddress(ADDRESS_ID);
            Mockito.verify(remoteOutputAddressService, Mockito.atLeast(1)).getRemoteAddressById(ADDRESS_ID);
            Assertions.assertNotNull(actuals);
            Assertions.assertEquals(beans, actuals);
        });
    }

    @Test
    void loadAllEmployees() {
        //PREPARE
        List<Employee> beans = List.of(bean, bean, bean);
        //EXECUTE
        Mockito.when(employeeServiceMock.loadAllEmployees()).thenReturn(beans);
        List<Employee> actuals = underTest.loadAllEmployees();
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Mockito.verify(employeeServiceMock, Mockito.atLeast(1)).loadAllEmployees();
            Assertions.assertNotNull(actuals);
            Assertions.assertEquals(beans.size(), actuals.size());
        });
    }

    @Test
    void loadRemoteAllAddresses() {
        //PREPARE
        List<Address> addresses = List.of(remoteAddress, remoteAddress, remoteAddress);
        //EXECUTE
        Mockito.when(remoteOutputAddressService.loadAllRemoteAddresses()).thenReturn(addresses);
        List<Address> actuals = underTest.loadRemoteAllAddresses();
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Mockito.verify(remoteOutputAddressService, Mockito.atLeast(1)).loadAllRemoteAddresses();
            Assertions.assertNotNull(actuals);
            Assertions.assertEquals(addresses.size(), actuals.size());
            actuals.forEach((var address) -> Assertions.assertEquals("Valenciennes", address.getCity()));
        });
    }
}