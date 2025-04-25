package AuthManager.rest.Controller;

import AuthManager.dto.AuthRequest;
import AuthManager.dto.AuthResponse;
import AuthManager.dto.RegisterRequest;
import AuthManager.entity.User;
import AuthManager.rest.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private static final String SECRET_KEY = "JRXDWF6bc/3gx6ZimVXNcs46zVtms0B9/bzJvT05+1PHCFetj4QKUfayFKpJzIgZQ/5DQ13NI/BS2s+Ri+KlPg==";

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterRequest> registerUser(@RequestBody RegisterRequest request) {
        ResponseEntity<RegisterRequest> response = authService.register(request);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        try {
            System.out.println("Authorization: " + token);

            // Remove "Bearer " prefix if present
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // Validate the JWT
            Jwts.parser()
                    .setSigningKey("JRXDWF6bc/3gx6ZimVXNcs46zVtms0B9/bzJvT05+1PHCFetj4QKUfayFKpJzIgZQ/5DQ13NI/BS2s+Ri+KlPg==")
                    .build()
                    .parseClaimsJws(token);

            System.out.println("Token is valid: " + token);
            return ResponseEntity.ok("Valid token");

        }  catch (Exception e) {
            System.out.println("General Token Error: " + e.getMessage());
            return ResponseEntity.status(401).body("Invalid token: " + e.getMessage());
        }
    }


}
