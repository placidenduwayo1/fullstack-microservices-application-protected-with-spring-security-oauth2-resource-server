package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.feignclients.proxies;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.ExceptionMsg;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.feignclients.models.EmployeeModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "aepc-api-spring-secur-oauth2-kafka-avro-bs-ms-employee",
        path = "/api-employee",
        configuration = SvcProjectFeignRequestAuthInterceptor.class,
        fallback = EmployeeServiceProxy.EmployeeServiceFallback.class)
@Qualifier(value = "employeeserviceproxy")
public interface EmployeeServiceProxy {
    @GetMapping("/employees/id/{employeeId}")
    EmployeeModel loadRemoteApiGetEmployee(@PathVariable(name = "employeeId") String employeeId);

    @GetMapping(value = "/employees/lastname/{lastname}")
    List<EmployeeModel> loadRemoteApiGetEmployeesByLastname(@PathVariable(name = "lastname") String lastname);

    // fault tolerance implementation with circuit breaker fallback
    @Component
    class EmployeeServiceFallback implements EmployeeServiceProxy {
        @Override
        public EmployeeModel loadRemoteApiGetEmployee(String employeeId) {
            return buildFakeModel();
        }

        @Override
        public List<EmployeeModel> loadRemoteApiGetEmployeesByLastname(String lastname) {
            return List.of(buildFakeModel());
        }

        private EmployeeModel buildFakeModel() {
            return EmployeeModel.builder()
                    .employeeId(ExceptionMsg.REMOTE_EMPLOYEE_API_EXCEPTION)
                    .firstname(ExceptionMsg.REMOTE_EMPLOYEE_API_EXCEPTION)
                    .lastname(ExceptionMsg.REMOTE_EMPLOYEE_API_EXCEPTION)
                    .email(ExceptionMsg.REMOTE_EMPLOYEE_API_EXCEPTION)
                    .hireDate(ExceptionMsg.REMOTE_EMPLOYEE_API_EXCEPTION)
                    .state(ExceptionMsg.REMOTE_EMPLOYEE_API_EXCEPTION)
                    .role(ExceptionMsg.REMOTE_EMPLOYEE_API_EXCEPTION)
                    .build();
        }
    }
}
