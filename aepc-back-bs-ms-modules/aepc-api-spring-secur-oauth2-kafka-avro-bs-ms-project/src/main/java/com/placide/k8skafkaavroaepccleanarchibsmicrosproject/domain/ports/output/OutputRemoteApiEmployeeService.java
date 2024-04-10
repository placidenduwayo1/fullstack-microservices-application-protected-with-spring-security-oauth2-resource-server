package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.RemoteEmployeeApiException;

import java.util.List;


public interface OutputRemoteApiEmployeeService {
    Employee getRemoteEmployeeAPI(String employeeId) throws RemoteEmployeeApiException;
    List<Employee> getRemoteEmployeesApiByLastname(String lastname);
}
