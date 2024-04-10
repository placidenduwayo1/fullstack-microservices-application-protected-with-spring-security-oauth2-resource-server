package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.proxies;


import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.models.ProjectModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "aepc-api-spring-secur-oauth2-kafka-avro-bs-ms-project",
        path = "/api-project",
        configuration = SvcEmployeeFeignRequestAuthInterceptor.class,
        fallback = ProjectServiceProxy.ProjectServiceProxyFallback.class)
@Qualifier(value="projectserviceproxy")
public interface ProjectServiceProxy {
    @GetMapping(value = "/projects/employees/id/{employeeId}")
    List<ProjectModel> getRemoteProjectsAssignedToEmployee(@PathVariable(name = "employeeId") String employeeId);

    // fault tolerance implementation with circuit breaker fallback
    @Component
    class ProjectServiceProxyFallback implements ProjectServiceProxy {
        @Override
        public List<ProjectModel> getRemoteProjectsAssignedToEmployee(String employeeId) {
            return Collections.emptyList();
        }
    }
}
