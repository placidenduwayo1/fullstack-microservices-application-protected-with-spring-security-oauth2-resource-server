package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.input.feignclients.proxy;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.input.feignclients.models.CompanyModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "aepc-api-spring-secur-oauth2-kafka-avro-bs-ms-company",
        path = "/api-company",
        configuration = SvcAddressFeignRequestAuthInterceptor.class,
        fallback = CompanyServiceProxy.CompanyServiceProxyFallback.class)
@Qualifier(value = "companyserviceproxy")
public interface CompanyServiceProxy {
    @GetMapping(value = "/companies/addresses/id/{addressId}")
    CompanyModel getRemoteCompanyAtAddress(@PathVariable(name = "addressId") String addressId);

    // fault tolerance implementation with circuit breaker fallback
    @Component
    class CompanyServiceProxyFallback implements CompanyServiceProxy {
        @Override
        public CompanyModel getRemoteCompanyAtAddress(String addressId) {
            return null;
        }
    }
}
