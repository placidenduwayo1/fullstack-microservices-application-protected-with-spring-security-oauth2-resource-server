package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.security.RSAPublicKeyRepatriation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(RSAPublicKeyRepatriation.class)
public class K8sKafkaAvroAepcCleanArchiBsMicrosEmployeeApplication {

	public static void main(String[] args) {
		new SpringApplication(K8sKafkaAvroAepcCleanArchiBsMicrosEmployeeApplication.class)
				.run(args);
	}

}
