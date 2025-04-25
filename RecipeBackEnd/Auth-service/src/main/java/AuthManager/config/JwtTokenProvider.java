package AuthManager.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {
    //private static final String SECRETKEY = "MySecretKey";
    //private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(SECRETKEY));

    public String generateToken(Authentication authentication)
    {
        String name = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime()+180000);


        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,"JRXDWF6bc/3gx6ZimVXNcs46zVtms0B9/bzJvT05+1PHCFetj4QKUfayFKpJzIgZQ/5DQ13NI/BS2s+Ri+KlPg==")
                .compact();
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey("JRXDWF6bc/3gx6ZimVXNcs46zVtms0B9/bzJvT05+1PHCFetj4QKUfayFKpJzIgZQ/5DQ13NI/BS2s+Ri+KlPg==").build()
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey("JRXDWF6bc/3gx6ZimVXNcs46zVtms0B9/bzJvT05+1PHCFetj4QKUfayFKpJzIgZQ/5DQ13NI/BS2s+Ri+KlPg==")
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage()); // Debugging
            return false;
        }
    }

}
