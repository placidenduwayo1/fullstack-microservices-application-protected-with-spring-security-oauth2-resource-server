package fr.placide.springsecurityservice.securitymanager.jwtgenerator;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface JWTGeneratorService {
    ResponseEntity<Map<String, Object>> generateToken(DtoToken dtoToken);
}
