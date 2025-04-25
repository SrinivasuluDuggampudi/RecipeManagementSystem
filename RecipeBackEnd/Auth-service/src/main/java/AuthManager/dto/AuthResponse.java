package AuthManager.dto;

import lombok.*;

@Getter
@Setter

public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";

    public AuthResponse(String token) {
        this.token = token;
    }
}