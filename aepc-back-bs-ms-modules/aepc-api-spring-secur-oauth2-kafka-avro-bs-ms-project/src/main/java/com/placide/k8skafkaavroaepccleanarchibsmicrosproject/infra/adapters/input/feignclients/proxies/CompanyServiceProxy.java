package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.feignclients.proxies;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.ExceptionMsg;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.adapters.input.feignclients.models.CompanyModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "aepc-api-spring-secur-oauth2-kafka-avro-bs-ms-company",
        path = "/api-company",
        configuration = SvcProjectFeignRequestAuthInterceptor.class,
        fallback = CompanyServiceProxy.CompanyServiceFallback.class)
@Qualifier(value = "companyserviceproxy")
public interface CompanyServiceProxy {
    @GetMapping(value = "/companies/id/{companyId}")
    CompanyModel loadRemoteCompanyApiById(@PathVariable(name = "companyId") String companyId);

    @GetMapping(value = "/companies/name/{companyName}")
    List<CompanyModel> loadRemoteCompanyApiByName(@PathVariable(name = "companyName") String companyName);

    @GetMapping(value = "/companies/agency/{agency}")
    List<CompanyModel> loadRemoteCompanyApiByAgency(@PathVariable(name = "agency") String agency);

    // fault tolerance implementation with circuit breaker fallback
    @Component
    class CompanyServiceFallback implements CompanyServiceProxy {
        @Override
        public CompanyModel loadRemoteCompanyApiById(String companyId) {
            return buildFakeModel();
        }

        @Override
        public List<CompanyModel> loadRemoteCompanyApiByName(String companyName) {
            return List.of(buildFakeModel());
        }

        @Override
        public List<CompanyModel> loadRemoteCompanyApiByAgency(String companyAgency) {
            return List.of(buildFakeModel());
        }

        private CompanyModel buildFakeModel() {
            return CompanyModel.builder()
                    .companyId(ExceptionMsg.REMOTE_COMPANY_API_EXCEPTION)
                    .name(ExceptionMsg.REMOTE_COMPANY_API_EXCEPTION)
                    .agency(ExceptionMsg.REMOTE_COMPANY_API_EXCEPTION)
                    .type(ExceptionMsg.REMOTE_COMPANY_API_EXCEPTION)
                    .connectedDate(ExceptionMsg.REMOTE_COMPANY_API_EXCEPTION)
                    .build();
        }
    }
}
