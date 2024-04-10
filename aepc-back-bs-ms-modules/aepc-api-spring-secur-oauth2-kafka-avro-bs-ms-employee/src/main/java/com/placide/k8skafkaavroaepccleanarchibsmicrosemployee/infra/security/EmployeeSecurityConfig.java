package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class EmployeeSecurityConfig {
    private final RSAPublicKeyRepatriation rsaPublicKeyRepatriation;
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaPublicKeyRepatriation.publicKey()).build();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authReq-> authReq.requestMatchers(
                        "/api-employee/**").hasAnyAuthority("SCOPE_ADMIN","SCOPE_HR"))
                .oauth2ResourceServer(oauth2->oauth2.jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }
}
