package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.input.feignclients.proxy;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.input.feignclients.models.EmployeeModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@FeignClient(
        name = "aepc-api-spring-secur-oauth2-kafka-avro-bs-ms-employee", path = "/api-employee",
        configuration = SvcAddressFeignRequestAuthInterceptor.class,
        fallback = EmployeeServiceProxy.EmployeeServiceProxyFallback.class)
@Qualifier(value = "employeeserviceproxy")
public interface EmployeeServiceProxy {
    @GetMapping(value = "/employees/addresses/{addressId}")
    List<EmployeeModel> getRemoteEmployeesLivingAtAddress(@PathVariable(name = "addressId") String addressId);

    // fault tolerance implementation with circuit breaker fallback
    @Component
    class EmployeeServiceProxyFallback implements EmployeeServiceProxy {
        @Override
        public List<EmployeeModel> getRemoteEmployeesLivingAtAddress(String addressId) {
            return Collections.emptyList();
        }
    }
}
