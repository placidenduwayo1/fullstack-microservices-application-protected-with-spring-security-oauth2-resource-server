package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.service;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.EmployeeAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.address.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions.EmployeeNotFoundException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions.RemoteApiAddressNotLoadedException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.usecase.Validator;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.models.AddressModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.proxies.AddressServiceProxy;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.mapper.AddressMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.mapper.EmployeeMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.models.EmployeeModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.repository.EmployeeRepository;
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
import java.util.Optional;
import java.util.UUID;


class OutputEmployeeServiceImplTest {
    @Mock
    private EmployeeRepository repository;
    @Mock
    private AddressServiceProxy addressServiceProxy;
    @InjectMocks
    private OutputEmployeeServiceImpl underTest;
    private Employee employee;
    private EmployeeAvro employeeAvro;
    private Address address;
    private static final String ADDRESS_ID = "1L";
    private static final String TOPIC1 = "topic1";
    private static final String TOPIC2 = "topic2";
    private static final String TOPIC3 = "topic3";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        String nom = "nduwayo";
        String prenom = "placide";
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
                .firstname(prenom)
                .lastname(nom)
                .email(Validator.setEmail(prenom, nom))
                .hireDate(Timestamp.from(Instant.now()).toString())
                .state("active")
                .type("software-engineer")
                .addressId(ADDRESS_ID)
                .address(address)
                .build();

        employeeAvro = EmployeeMapper.fromBeanToAvro(employee);
    }

    @Test
    void consumeKafkaEventEmployeeCreate() {
        //PREPARE
        //EXECUTE
        Employee consumed = underTest.consumeKafkaEventEmployeeCreate(employeeAvro, TOPIC1);
        //VERIFY
        Assertions.assertAll("grp of assertions", () -> {
            Assertions.assertNotNull(consumed);
            Assertions.assertEquals(consumed.getEmployeeId(), employee.getEmployeeId());
            Assertions.assertEquals(employee.getEmployeeId(), employeeAvro.getEmployeeId());
        });
    }

    @Test
    void saveEmployee() {
        //PREPARE
        EmployeeModel model = EmployeeMapper.toModel(employee);
        //EXECUTE
        Employee consumed = underTest.consumeKafkaEventEmployeeCreate(employeeAvro, TOPIC1);
        EmployeeModel consumedModel = EmployeeMapper.toModel(consumed);
        Mockito.when(repository.save(consumedModel)).thenReturn(model);
        Employee saved = underTest.saveEmployee(consumed);
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Mockito.verify(repository).save(consumedModel),
                () -> Assertions.assertNotNull(saved));
    }

    @Test
    void getEmployeeById() throws EmployeeNotFoundException {
        //PREPARE
        String employeeId = "uuid";
        EmployeeModel model = EmployeeMapper.toModel(employee);
        //EXECUTE
        Mockito.when(repository.findById(employeeId)).thenReturn(Optional.of(model));
        Employee obtained = underTest.getEmployeeById(employeeId);
        //VERIFY
        Assertions.assertAll("grp of assertions", () -> {
            Mockito.verify(repository, Mockito.atLeast(1)).findById(employeeId);
            Assertions.assertNotNull(obtained);
        });
    }

    @Test
    void loadEmployeesByRemoteAddress() throws RemoteApiAddressNotLoadedException {
        //PREPARE
        AddressModel addressModel = AddressMapper.toModel(address);
        List<EmployeeModel> employeeModels = List.of(EmployeeMapper.toModel(employee));
        //EXECUTE
        Mockito.when(addressServiceProxy.loadRemoteAddressById(ADDRESS_ID)).thenReturn(addressModel);
        Mockito.when(repository.findByAddressId(addressModel.getAddressId())).thenReturn(employeeModels);
        List<Employee> employees = underTest.loadEmployeesByRemoteAddress(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Assertions.assertEquals(1, employees.size()),
                () -> Mockito.verify(addressServiceProxy).loadRemoteAddressById(ADDRESS_ID),
                () -> Mockito.verify(repository).findByAddressId(ADDRESS_ID));
    }

    @Test
    void loadEmployeeByInfo() {
        //PREPARE
        String firstname = "Placide";
        String lastname = "Nduwayo";
        String state = "active";
        String type = "software-engineer";
        EmployeeModel model = EmployeeMapper.toModel(employee);
        //EXECUTE
        Mockito.when(repository.findByFirstnameAndLastnameAndStateAndRoleAndAddressId(firstname, lastname, state, type, ADDRESS_ID))
                .thenReturn(List.of(model));
        List<Employee> employees = underTest.loadEmployeeByInfo(firstname, lastname, state, type, ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Assertions.assertEquals(1, employees.size()),
                () -> Mockito.verify(repository).findByFirstnameAndLastnameAndStateAndRoleAndAddressId(firstname, lastname, state, type, ADDRESS_ID)
        );
    }

    @Test
    void loadAllEmployees() {
        //PREPARE
        EmployeeModel model = EmployeeMapper.toModel(employee);
        //EXECUTE
        Mockito.when(repository.findAll()).thenReturn(List.of(model));
        List<Employee> employees = underTest.loadAllEmployees();
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Assertions.assertEquals(1, employees.size()),
                () -> Mockito.verify(repository).findAll()
        );
    }

    @Test
    void consumeKafkaEventEmployeeDelete() {
        //PREPARE

        //EXECUTE
        Employee consumed = underTest.consumeKafkaEventEmployeeDelete(employeeAvro, TOPIC2);
        //VERIFY
        Assertions.assertAll("assertions", () -> {
            Assertions.assertEquals(consumed.getEmployeeId(), employee.getEmployeeId());
            Assertions.assertEquals(consumed.getAddress().getCity(), employee.getAddress().getCity());
            Assertions.assertEquals(consumed.getAddress().getCountry(), employee.getAddress().getCountry());
        });
    }

    @Test
    void deleteEmployee() throws EmployeeNotFoundException, RemoteApiAddressNotLoadedException {
        //PREPARE
        String employeeId = "uuid";
        EmployeeModel employeeModel = EmployeeMapper.toModel(employee);
        AddressModel addressModel = AddressMapper.toModel(address);
        //EXECUTE
        Mockito.when(repository.findById(employeeId)).thenReturn(Optional.of(employeeModel));
        Employee obtained = underTest.getEmployeeById(employeeId);
        Mockito.when(addressServiceProxy.loadRemoteAddressById(ADDRESS_ID)).thenReturn(addressModel);
        String msg = underTest.deleteEmployee(employeeId);

        //VERIFY
        Assertions.assertAll("grp of assertions", () -> {
            Assertions.assertNotNull(obtained);
            Mockito.verify(repository, Mockito.atLeast(1)).findById(employeeId);
            Assertions.assertNotNull(msg);
        });
    }

    @Test
    void consumeKafkaEventEmployeeEdit() {
        //PREPARE
        //EXECUTE
        Employee consumed = underTest.consumeKafkaEventEmployeeEdit(employeeAvro, TOPIC3);
        //VERIFY
        Assertions.assertNotNull(consumed);
    }

    @Test
    void editEmployee() throws EmployeeNotFoundException {
        //PREPARE
        String firstname = "Placide";
        String lastname = "Nduwayo";
        Employee updated = new Employee.EmployeeBuilder()
                .employeeId(employee.getEmployeeId())
                .firstname(firstname)
                .lastname(lastname)
                .email(Validator.setEmail(firstname,lastname))
                .hireDate(employee.getHireDate())
                .state("active")
                .type("software-engineer")
                .addressId(ADDRESS_ID)
                .address(address)
                .build();
        EmployeeModel model = EmployeeMapper.toModel(updated);
        String id = "uuid";
        //EXECUTE
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(model));
        Employee obtained = underTest.getEmployeeById(id);
        Employee consumed = underTest.consumeKafkaEventEmployeeEdit(employeeAvro, TOPIC3);
        Mockito.when(repository.save(EmployeeMapper.toModel(consumed))).thenReturn(model);
        Employee saved = underTest.editEmployee(consumed);
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Mockito.verify(repository, Mockito.atLeast(1)).findById(id),
                () -> Mockito.verify(repository, Mockito.atLeast(1)).save(EmployeeMapper.toModel(consumed)),
                () -> Assertions.assertNotNull(saved),
                () -> Assertions.assertNotNull(obtained)
        );
    }

    @Test
    void getRemoteAddressById() throws RemoteApiAddressNotLoadedException {
        //PREPARE
        AddressModel model = AddressMapper.toModel(address);
        //EXECUTE
        Mockito.when(addressServiceProxy.loadRemoteAddressById(ADDRESS_ID)).thenReturn(model);
        Address obtained = underTest.getRemoteAddressById(ADDRESS_ID);
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Mockito.verify(addressServiceProxy).loadRemoteAddressById(ADDRESS_ID),
                () -> Assertions.assertNotNull(obtained));
    }

    @Test
    void loadAllRemoteAddresses() {
        //PREPARE
        List<Address> addresses = List.of(address, address, address);
        List<AddressModel> models = addresses.stream().map(AddressMapper::toModel).toList();
        //EXECUTE
        Mockito.when(addressServiceProxy.loadAllRemoteAddresses()).thenReturn(models);
        List<Address> obtained = underTest.loadAllRemoteAddresses();
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Mockito.verify(addressServiceProxy).loadAllRemoteAddresses(),
                () -> Assertions.assertFalse(obtained.isEmpty()),
                () -> Assertions.assertEquals(3, obtained.size()));
    }
}