package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.config;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.output.RemoteOutputAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.output.OutputEmployeeService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.ports.output.RemoteOutputProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.usecase.EmployeeUseCase;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.service.OutputKafkaProducerEmployeeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UseCaseConfig {
    @Bean
    public EmployeeUseCase configUseCase(
            OutputKafkaProducerEmployeeServiceImpl kafkaProducer,
            OutputEmployeeService employeeService,
            RemoteOutputAddressService addressService,
            RemoteOutputProjectService remoteOutputProjectService) {
        return new EmployeeUseCase(kafkaProducer, employeeService, addressService, remoteOutputProjectService);

    }
}
