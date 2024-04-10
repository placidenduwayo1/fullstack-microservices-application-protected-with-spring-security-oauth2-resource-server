package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.config;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output.OutputKafkaProducerProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output.OutputProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output.OutputRemoteApiCompanyService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output.OutputRemoteApiEmployeeService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.usecase.UseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UseCaseConfig {
    @Bean
    public UseCase configureUseCase(OutputKafkaProducerProjectService kafkaProducerService,
                                    OutputProjectService projectService,
                                    OutputRemoteApiEmployeeService employeeAPIService,
                                    OutputRemoteApiCompanyService companyAPIService){
        return new UseCase(kafkaProducerService,projectService,employeeAPIService, companyAPIService);
    }
}
