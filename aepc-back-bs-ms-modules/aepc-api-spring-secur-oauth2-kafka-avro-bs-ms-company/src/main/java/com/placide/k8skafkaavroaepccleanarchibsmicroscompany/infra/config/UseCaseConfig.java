package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.config;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output.OutputCompanyService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output.OutputRemoteAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output.OutputRemoteProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.usecase.CompanyUseCase;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.services.OutputKafkaProducerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UseCaseConfig {
    @Bean
    CompanyUseCase configCompanyUseCase(
            OutputKafkaProducerServiceImpl kafkaProducer,
            OutputCompanyService companyService,
            OutputRemoteAddressService outputRemoteAddressService,
            OutputRemoteProjectService outputRemoteProjectService) {
        return new CompanyUseCase(kafkaProducer, companyService, outputRemoteAddressService, outputRemoteProjectService);
    }
}
