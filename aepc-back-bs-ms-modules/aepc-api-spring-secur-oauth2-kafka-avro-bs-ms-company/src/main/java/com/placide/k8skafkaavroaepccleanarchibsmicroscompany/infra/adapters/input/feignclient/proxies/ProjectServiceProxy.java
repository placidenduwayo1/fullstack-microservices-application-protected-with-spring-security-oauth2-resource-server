package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.proxies;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.CompanyNotFoundException;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.models.ProjectModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "aepc-api-spring-secur-oauth2-kafka-avro-bs-ms-project",
        path = "/api-project",
        configuration = SvcCompanyFeignRequestAuthInterceptor.class,
        fallback = ProjectServiceProxy.ProjectServiceProxyFallback.class)
@Qualifier(value = "projectserviceproxy")
public interface ProjectServiceProxy {
    @GetMapping(value = "/projects/companies/id/{companyId}")
    List<ProjectModel> loadRemoteProjectsOfCompany(@PathVariable(name = "companyId") String companyId) throws
            CompanyNotFoundException;


    // fault tolerance implementation with circuit breaker fallback
    @Component
    class ProjectServiceProxyFallback implements ProjectServiceProxy {
        @Override
        public List<ProjectModel> loadRemoteProjectsOfCompany(String companyId) {
            return Collections.emptyList();
        }
    }
}
