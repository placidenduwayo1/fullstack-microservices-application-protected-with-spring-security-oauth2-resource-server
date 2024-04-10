package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.config;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.output.OutputKafkaProducerAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.output.OutputAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.output.OutputRemoteCompanyService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.output.OutputRemoteEmployeeService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.usecase.AddressUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UseCaseConfig {
    @Bean
    AddressUseCase configInputAddressService(
            OutputKafkaProducerAddressService kafkaProducer,
            OutputAddressService outAddressService,
            OutputRemoteCompanyService outputRemoteCompanyService,
            OutputRemoteEmployeeService outputRemoteEmployeeService) {
        return new AddressUseCase(kafkaProducer, outAddressService, outputRemoteCompanyService, outputRemoteEmployeeService);
    }
}
