package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.service;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.EmployeeAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.address.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions.EmployeeNotFoundException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions.RemoteApiAddressNotLoadedException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.output.RemoteOutputAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.output.OutputEmployeeService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.output.RemoteOutputProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.models.ProjectModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.proxies.ProjectServiceProxy;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.mapper.AddressMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.mapper.EmployeeMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.models.AddressModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.proxies.AddressServiceProxy;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.mapper.ProjectMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.models.EmployeeModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class OutputEmployeeServiceImpl implements OutputEmployeeService, RemoteOutputAddressService, RemoteOutputProjectService {
    private final EmployeeRepository repository;
    private final AddressServiceProxy addressProxy;
    private final ProjectServiceProxy projectServiceProxy;

    public OutputEmployeeServiceImpl(EmployeeRepository repository,
                                     @Qualifier(value = "addressserviceproxy") AddressServiceProxy addressProxy,
                                     @Qualifier(value = "projectserviceproxy") ProjectServiceProxy projectServiceProxy) {
        this.repository = repository;
        this.addressProxy = addressProxy;
        this.projectServiceProxy = projectServiceProxy;
    }

    @Override
    @KafkaListener(topics = "avro-employees-created", groupId = "employee-group-id")
    public Employee consumeKafkaEventEmployeeCreate(@Payload EmployeeAvro employeeAvro,
                                                    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Employee employee = EmployeeMapper.fromAvroToBean(employeeAvro);
        log.info("employee to add: <{}> consumed from topic: <{}>", employee, topic);
        return employee;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        EmployeeAvro employeeAvro = EmployeeMapper.fromBeanToAvro(employee);
        Employee consumed = consumeKafkaEventEmployeeCreate(employeeAvro, "avro-employees-created");
        EmployeeModel savedEmployee = repository.save(EmployeeMapper.toModel(consumed));
        return EmployeeMapper.toBean(savedEmployee);
    }

    private List<Employee> employeeModelToBean(List<EmployeeModel> models) {
        return models.stream()
                .map(EmployeeMapper::toBean)
                .toList();
    }

    @Override
    public Employee getEmployeeById(String employeeId) throws EmployeeNotFoundException {
        EmployeeModel model = repository.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException("Employee Not found Exception"));
        return EmployeeMapper.toBean(model);
    }

    @Override
    public List<Employee> loadEmployeesByRemoteAddress(String addressId) throws RemoteApiAddressNotLoadedException {
        AddressModel model = addressProxy.loadRemoteAddressById(addressId);
        List<EmployeeModel> models = repository.findByAddressId(model.getAddressId());
        return employeeModelToBean(models);
    }

    @Override
    public List<Employee> loadEmployeeByInfo(String firstname, String lastname, String state, String type, String addressId) {
        return employeeModelToBean(repository
                .findByFirstnameAndLastnameAndStateAndRoleAndAddressId(firstname, lastname, state, type, addressId)
        );
    }

    @Override
    public List<Employee> loadAllEmployees() {
        return employeeModelToBean(repository.findAll());
    }

    @Override
    @KafkaListener(topics = "avro-employees-deleted", groupId = "-employee-group-id}")
    public Employee consumeKafkaEventEmployeeDelete(@Payload EmployeeAvro employeeAvro,
                                                    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Employee employee = EmployeeMapper.fromAvroToBean(employeeAvro);
        log.info("employee to delete:<{}> consumed from topic:<{}>", employee, topic);
        return employee;
    }

    @Override
    public String deleteEmployee(String employeeId) throws EmployeeNotFoundException {
        Employee bean = getEmployeeById(employeeId);
        repository.deleteById(bean.getEmployeeId());
        return "employee <" + bean + "> is deleted";
    }

    @Override
    @KafkaListener(topics = "avro-employee-edited", groupId = "employee-group-id")
    public Employee consumeKafkaEventEmployeeEdit(@Payload EmployeeAvro employeeAvro,
                                                  @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Employee employee = EmployeeMapper.fromAvroToBean(employeeAvro);
        log.info("employee to update :<{}> consumed from topic:<{}>", employee, topic);
        return employee;
    }

    @Override
    public Employee editEmployee(Employee employee) {
        EmployeeAvro avro = EmployeeMapper.fromBeanToAvro(employee);
        Employee consumed = consumeKafkaEventEmployeeEdit(avro, "avro-employee-edited");
        EmployeeModel mapped = EmployeeMapper.toModel(consumed);
        return EmployeeMapper.toBean(repository.save(mapped));
    }

    @Override
    public List<Employee> getEmployeesByLastname(String employeeLastname) {
        List<EmployeeModel> models = repository.findByLastname(employeeLastname);
        return models.stream()
                .map(EmployeeMapper::toBean)
                .toList();
    }

    @Override
    public Address getRemoteAddressById(String addressId) throws RemoteApiAddressNotLoadedException {
        return AddressMapper.toBean(addressProxy.loadRemoteAddressById(addressId));

    }

    @Override
    public List<Address> loadAllRemoteAddresses() {
        List<AddressModel> models = addressProxy.loadAllRemoteAddresses();
        return models.stream()
                .map(AddressMapper::toBean)
                .toList();
    }

    @Override
    public List<Address> loadRemoteAddressesByCity(String city) {
        return addressProxy.loadRemoteAddressesByCity(city)
                .stream()
                .map(AddressMapper::toBean)
                .toList();
    }

    @Override
    public List<Project> loadRemoteProjectsAssignedToEmployee(String employeeId) throws EmployeeNotFoundException {
        Employee employee = EmployeeMapper.toBean(repository.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not found Exception")));
        List<ProjectModel> projectModels = projectServiceProxy.getRemoteProjectsAssignedToEmployee(employee.getEmployeeId());
        return projectModels.stream()
                .map(ProjectMapper::toBean)
                .toList();
    }
}
