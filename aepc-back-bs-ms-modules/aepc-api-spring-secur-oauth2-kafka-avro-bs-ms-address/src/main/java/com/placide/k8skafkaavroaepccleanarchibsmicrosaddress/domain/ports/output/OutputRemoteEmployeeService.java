package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.output;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.AddressNotFoundException;

import java.util.List;

public interface OutputRemoteEmployeeService {
    List<Employee> getRemoteEmployeesLivingAtAddress(String addressId) throws AddressNotFoundException;
}
