package com.placide.k8skafkaavroaepccleanarchibsmicroscompany;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.security.RSAPublicKeyRepatriation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(RSAPublicKeyRepatriation.class)
public class K8sKafkaAvroAepcCleanArchiBsMicrosCompanyApplication {

	public static void main(String[] args) {
		new SpringApplication(K8sKafkaAvroAepcCleanArchiBsMicrosCompanyApplication.class)
				.run(args);
	}

}
