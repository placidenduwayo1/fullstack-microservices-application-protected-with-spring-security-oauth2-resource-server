package com.placide.k8skafkaavroaepccleanarchibsmicrosproject;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.infra.security.RSAPublicKeyRepatriation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(RSAPublicKeyRepatriation.class)
public class K8sKafkaAvroAepcCleanArchiBsMicrosProjectApplication {

	public static void main(String[] args) {
		new SpringApplication(K8sKafkaAvroAepcCleanArchiBsMicrosProjectApplication.class)
				.run(args);
	}

}
