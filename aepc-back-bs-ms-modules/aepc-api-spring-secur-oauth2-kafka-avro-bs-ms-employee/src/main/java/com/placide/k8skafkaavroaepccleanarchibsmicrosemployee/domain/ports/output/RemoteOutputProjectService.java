package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.output;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions.EmployeeNotFoundException;

import java.util.List;

public interface RemoteOutputProjectService {
    List<Project> loadRemoteProjectsAssignedToEmployee(String employeeId) throws EmployeeNotFoundException;
}
