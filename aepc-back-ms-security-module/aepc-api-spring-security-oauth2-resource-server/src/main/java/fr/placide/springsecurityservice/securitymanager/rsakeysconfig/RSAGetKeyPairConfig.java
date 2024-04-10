package fr.placide.springsecurityservice.securitymanager.rsakeysconfig;


import org.springframework.boot.context.properties.ConfigurationProperties;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
@ConfigurationProperties(prefix = "rsa")
public record RSAGetKeyPairConfig(RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
}
