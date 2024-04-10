package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.input;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.RemoteEmployeeApiException;

import java.util.List;


public interface InputRemoteApiEmployeeService {
    Employee getRemoteEmployeeAPI(String employeeId) throws RemoteEmployeeApiException;
    List<Employee> getRemoteEmployeesApiByLastname(String lastname);
}
