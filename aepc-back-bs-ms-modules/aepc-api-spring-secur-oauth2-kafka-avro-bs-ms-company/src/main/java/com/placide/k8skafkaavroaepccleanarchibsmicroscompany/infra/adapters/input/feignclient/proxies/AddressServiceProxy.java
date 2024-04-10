package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.proxies;


import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.ExceptionMsg;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.RemoteApiAddressNotLoadedException;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.models.AddressModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "aepc-api-spring-secur-oauth2-kafka-avro-bs-ms-address",
        path = "/api-address",
        configuration = SvcCompanyFeignRequestAuthInterceptor.class,
        fallback = AddressServiceProxy.AddressServiceProxyFallback.class)
@Qualifier(value = "addressserviceproxy")
public interface AddressServiceProxy {
    @GetMapping(value = "/addresses/id/{addressId}")
    AddressModel loadRemoteApiGetAddressById(@PathVariable(name = "addressId") String addressId) throws
            RemoteApiAddressNotLoadedException;

    @GetMapping(value = "/addresses/city/{city}")
    List<AddressModel> loadRemoteApiAddressesByCity(@PathVariable(name = "city") String city);

    // fault tolerance implementation with circuit breaker fallback
    @Component
    @Slf4j
    class AddressServiceProxyFallback implements AddressServiceProxy {

        @Override
        public AddressModel loadRemoteApiGetAddressById(String addressId) {
            AddressModel resilience = buildFakeAddress();
            log.info("[Fallback] resilience management {}", resilience);
            return resilience;
        }

        @Override
        public List<AddressModel> loadRemoteApiAddressesByCity(String city) {
            return List.of(buildFakeAddress());
        }

        private AddressModel buildFakeAddress() {
            return AddressModel.builder()
                    .addressId(ExceptionMsg.REMOTE_ADDRESS_API_EXCEPTION)
                    .num(-1)
                    .street(ExceptionMsg.REMOTE_ADDRESS_API_EXCEPTION)
                    .pb(-1)
                    .city(ExceptionMsg.REMOTE_ADDRESS_API_EXCEPTION)
                    .country(ExceptionMsg.REMOTE_ADDRESS_API_EXCEPTION)
                    .build();
        }
    }
}