package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.security.RSAPublicKeyRepatriation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(value = RSAPublicKeyRepatriation.class)
public class K8sKafkaAvroAepcCleanArchiBsMicrosAddressApplication {
	public static void main(String[] args) {
		SpringApplication.run(K8sKafkaAvroAepcCleanArchiBsMicrosAddressApplication.class, args);
	}

}
