package AuthManager.rest.service;

import AuthManager.dto.AuthRequest;
import AuthManager.dto.AuthResponse;
import AuthManager.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
   /* String register(User user);
    String login(String username, String password);*/
    Boolean isUserLoggedIn(String username);

    ResponseEntity<RegisterRequest> register(RegisterRequest registerRequest);

    ResponseEntity<AuthResponse> login(AuthRequest authRequest);
}
