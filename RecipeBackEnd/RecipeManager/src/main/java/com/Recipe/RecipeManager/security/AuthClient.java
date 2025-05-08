package com.Recipe.RecipeManager.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "http://localhost:8082/auth")
public interface AuthClient {

    @GetMapping("/validate")
    ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token);
}
