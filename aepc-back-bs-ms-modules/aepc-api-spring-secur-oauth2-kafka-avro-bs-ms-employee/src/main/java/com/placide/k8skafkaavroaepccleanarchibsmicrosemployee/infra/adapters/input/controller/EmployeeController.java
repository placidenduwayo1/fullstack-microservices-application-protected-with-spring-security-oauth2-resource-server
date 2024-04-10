package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.controller;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.address.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions.*;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.input.InputEmployeeService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.input.RemoteInputAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.models.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api-employee")
public class EmployeeController {
    private final InputEmployeeService inputEmployeeService;
    private final RemoteInputAddressService remoteInputAddressService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getWelcome() {
        return new ResponseEntity<>("Welcome to business microservice employee that managing employees", HttpStatus.OK);
    }

    @PostMapping(value = "/employees")
    public List<String> produceConsumeAndSaveEmployee(@RequestBody EmployeeDto employeeDto) throws
            EmployeeTypeInvalidException, EmployeeEmptyFieldsException, EmployeeStateInvalidException,
            RemoteApiAddressNotLoadedException, EmployeeAlreadyExistsException {

        Employee consumed = inputEmployeeService.produceKafkaEventEmployeeCreate(employeeDto);
        Employee saved = inputEmployeeService.createEmployee(consumed);
        return List.of("produced consumed:" + consumed, "saved:" + saved);
    }

    @GetMapping(value = "/employees/addresses/id/{addressId}")
    public Address getRemoteAddress(@PathVariable(name = "addressId") String addressId) throws
            RemoteApiAddressNotLoadedException {
        return remoteInputAddressService.getRemoteAddressById(addressId);
    }

    @GetMapping(value = "/employees/addresses")
    public List<Address> getRemoteAddresses() {
        return remoteInputAddressService.loadRemoteAllAddresses();
    }

    @GetMapping(value = "/employees/addresses/{addressId}")
    public List<Employee> loadEmployeesOnGivenAddress(@PathVariable(name = "addressId") String addressId) throws
            RemoteApiAddressNotLoadedException {
        return inputEmployeeService.loadEmployeesByRemoteAddress(addressId);
    }

    @GetMapping(value = "/employees")
    public List<Employee> loadAllEmployees() {
        return inputEmployeeService.loadAllEmployees();
    }

    @GetMapping(value = "/employees/id/{id}")
    public Employee getEmployee(@PathVariable(name = "id") String id) throws EmployeeNotFoundException, RemoteApiAddressNotLoadedException {
        return inputEmployeeService.getEmployeeById(id);
    }

    @DeleteMapping(value = "/employees/id/{id}")
    public void delete(@PathVariable(name = "id") String id) throws EmployeeNotFoundException, RemoteApiAddressNotLoadedException,
            EmployeeAlreadyAssignedProjectException {
        Employee consumed = inputEmployeeService.produceKafkaEventEmployeeDelete(id);
        inputEmployeeService.deleteEmployee(consumed.getEmployeeId());
    }

    @PutMapping(value = "/employees/id/{id}")
    public List<String> update(@PathVariable(name = "id") String id, @RequestBody EmployeeDto dto) throws EmployeeTypeInvalidException,
            EmployeeEmptyFieldsException, EmployeeStateInvalidException, RemoteApiAddressNotLoadedException, EmployeeNotFoundException {
        Employee consumed = inputEmployeeService.produceKafkaEventEmployeeEdit(dto, id);
        Employee saved = inputEmployeeService.editEmployee(consumed);
        return List.of("produced consumed:" + consumed, "saved:" + saved);
    }

    @GetMapping(value = "/employees/lastname/{employeeLastname}")
    public List<Employee> getEmployeeByLastname(@PathVariable(name = "employeeLastname") String employeeLastname) {
        return inputEmployeeService.getEmployeesByLastname(employeeLastname);
    }

    @GetMapping(value = "/employees/addresses/city/{city}")
    public List<Employee> getEmployeesByRemoteAddressCity(@PathVariable(name = "city") String city) throws RemoteApiAddressNotLoadedException {
        return inputEmployeeService.loadEmployeesByRemoteAddressCity(city);
    }
    @GetMapping(value = "/projects/employees/id/{employeeId}")
    public List<Project> getRemoteProjectsAssignedToEmployeeId(@PathVariable(name = "employeeId") String employeeId) throws EmployeeNotFoundException {
        return inputEmployeeService.getRemoteProjectsAssignedToEmployeeId(employeeId);
    }
}
