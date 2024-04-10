package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.proxies;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions.ExceptionsMsg;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions.RemoteApiAddressNotLoadedException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.models.AddressModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "aepc-api-spring-secur-oauth2-kafka-avro-bs-ms-address",
        path = "/api-address",
        configuration = SvcEmployeeFeignRequestAuthInterceptor.class,
        fallback = AddressServiceProxy.AddressServiceProxyFallback.class)
@Qualifier(value = "addressserviceproxy")
public interface AddressServiceProxy {
    @GetMapping(value = "/addresses/id/{addressId}")
   AddressModel loadRemoteAddressById(@PathVariable(name = "addressId") String addressId)
            throws RemoteApiAddressNotLoadedException;
    @GetMapping(value = "/addresses")
    List<AddressModel> loadAllRemoteAddresses();
    @GetMapping(value = "/addresses/city/{city}")
    List<AddressModel> loadRemoteAddressesByCity(@PathVariable(name = "city") String city);

    // fault tolerance implementation with circuit breaker fallback
    @Component
    class AddressServiceProxyFallback implements AddressServiceProxy {
        @Override
        public AddressModel loadRemoteAddressById(String addressId) {
            return buildFakeModel();
        }
        @Override
        public List<AddressModel> loadAllRemoteAddresses() {
            return List.of(buildFakeModel());
        }

        @Override
        public List<AddressModel> loadRemoteAddressesByCity(String city) {
            return List.of(buildFakeModel());
        }
        private AddressModel buildFakeModel(){
            return AddressModel.builder()
                    .addressId(ExceptionsMsg.REMOTE_ADDRESS_API_EXCEPTION)
                    .num(-1)
                    .street(ExceptionsMsg.REMOTE_ADDRESS_API_EXCEPTION)
                    .pb(-1)
                    .city(ExceptionsMsg.REMOTE_ADDRESS_API_EXCEPTION)
                    .country(ExceptionsMsg.REMOTE_ADDRESS_API_EXCEPTION)
                    .build();
        }
    }
}