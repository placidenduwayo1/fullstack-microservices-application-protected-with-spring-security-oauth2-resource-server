package fr.placide.springsecurityservice;

import fr.placide.springsecurityservice.securitymanager.rsakeysconfig.RSAGetKeyPairConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RSAGetKeyPairConfig.class)
public class SpringSecurityServiceApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringSecurityServiceApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}
