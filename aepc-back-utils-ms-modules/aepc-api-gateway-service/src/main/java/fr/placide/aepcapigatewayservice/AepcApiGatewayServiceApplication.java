package fr.placide.aepcapigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AepcApiGatewayServiceApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication application = new SpringApplication(AepcApiGatewayServiceApplication.class);
		Thread.sleep(8000);
		application.run(args);
	}

}
