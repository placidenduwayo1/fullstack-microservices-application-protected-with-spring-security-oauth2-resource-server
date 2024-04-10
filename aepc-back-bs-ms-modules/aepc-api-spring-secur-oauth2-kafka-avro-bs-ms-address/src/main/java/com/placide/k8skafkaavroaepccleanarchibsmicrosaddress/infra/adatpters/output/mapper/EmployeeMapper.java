package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.mapper;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.input.feignclients.models.EmployeeModel;
import org.springframework.beans.BeanUtils;

public class EmployeeMapper {
    private EmployeeMapper(){}
    public static Employee toBean(EmployeeModel model){
        Employee bean = Employee.builder().build();
        BeanUtils.copyProperties(model,bean);
        return bean;
    }
}
