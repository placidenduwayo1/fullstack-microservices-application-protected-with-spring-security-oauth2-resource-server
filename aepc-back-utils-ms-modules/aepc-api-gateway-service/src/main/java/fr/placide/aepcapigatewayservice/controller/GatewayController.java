package fr.placide.aepcapigatewayservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GatewayController {
    @Value("${message}")
    private String [] welcome;
    @GetMapping(value = "")
    public ResponseEntity<Map<String, String>> getWelcome(){
        return new ResponseEntity<>(Map.of(welcome[0], welcome[1]), HttpStatus.OK);
    }
}
