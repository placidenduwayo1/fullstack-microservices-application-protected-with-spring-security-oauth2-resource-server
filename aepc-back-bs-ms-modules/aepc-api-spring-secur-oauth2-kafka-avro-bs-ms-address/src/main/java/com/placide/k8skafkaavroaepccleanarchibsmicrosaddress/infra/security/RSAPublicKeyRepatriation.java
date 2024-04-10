package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPublicKey;
@ConfigurationProperties(prefix = "rsa")
public record RSAPublicKeyRepatriation(RSAPublicKey publicKey) {
}
