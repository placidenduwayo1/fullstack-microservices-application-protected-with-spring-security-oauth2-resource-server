package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.mapper;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.avrobeans.EmployeeAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.employee.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.models.EmployeeDto;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.models.EmployeeModel;
import org.springframework.beans.BeanUtils;

public class EmployeeMapper {

    private EmployeeMapper(){}
    public static Employee toBean(EmployeeModel model){
        Employee employee = new Employee.EmployeeBuilder().build();
        BeanUtils.copyProperties(model,employee);
        return employee;
    }
    public static EmployeeModel toModel(Employee bean){
        EmployeeModel model = EmployeeModel.builder().build();
        BeanUtils.copyProperties(bean, model);
        return model;
    }

    public static Employee fromDto(EmployeeDto dto){
        Employee bean = new Employee.EmployeeBuilder().build();
        BeanUtils.copyProperties(dto,bean);
        return bean;
    }

    public static EmployeeAvro fromBeanToAvro(Employee employee) {
        Address addressAvro = Address.newBuilder()
                .setAddressId(employee.getAddressId())
                .setNum(employee.getAddress().getNum())
                .setStreet(employee.getAddress().getStreet())
                .setPb(employee.getAddress().getPb())
                .setCity(employee.getAddress().getCity())
                .setCountry(employee.getAddress().getCountry())
                .build();

        return EmployeeAvro.newBuilder()
                .setEmployeeId(employee.getEmployeeId())
                .setFirstname(employee.getFirstname())
                .setLastname(employee.getLastname())
                .setEmail(employee.getEmail())
                .setHireDate(employee.getHireDate())
                .setState(employee.getState())
                .setRole(employee.getRole())
                .setAddressId(employee.getAddressId())
                .setAddress(addressAvro)
                .build();
    }

    public static Employee fromAvroToBean(EmployeeAvro employeeAvro){
        com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.address.Address address =
                com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.address.Address.builder()
                        .addressId(employeeAvro.getAddressId())
                        .num(employeeAvro.getAddress().getNum())
                        .street(employeeAvro.getAddress().getStreet())
                        .pb(employeeAvro.getAddress().getPb())
                        .city(employeeAvro.getAddress().getCity())
                        .country(employeeAvro.getAddress().getCountry())
                        .build();

        return new Employee.EmployeeBuilder()
                .employeeId(employeeAvro.getEmployeeId())
                .firstname(employeeAvro.getFirstname())
                .lastname(employeeAvro.getLastname())
                .email(employeeAvro.getEmail())
                .hireDate(employeeAvro.getHireDate())
                .state(employeeAvro.getState())
                .type(employeeAvro.getRole())
                .addressId(employeeAvro.getAddressId())
                .address(address)
                .build();
    }
}
