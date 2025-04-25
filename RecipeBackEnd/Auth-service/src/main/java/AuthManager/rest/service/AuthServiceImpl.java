package AuthManager.rest.service;

import AuthManager.config.JwtTokenProvider;
import AuthManager.dto.AuthRequest;
import AuthManager.dto.AuthResponse;
import AuthManager.dto.RegisterRequest;
import AuthManager.entity.User;
import AuthManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Set<String> loggedUsers = new HashSet<>();
  /*  @Override
    public String register(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent())
        {
            return "User already Present.";
        }
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "User created successfully.";
        }

    }

    @Override
    public String login(String username, String password) {
        if(userRepository.findByUsername(username).isPresent()
                && passwordEncoder.matches(password,userRepository.findByUsername(username).get().getPassword())) {
            loggedUsers.add(username);
            return "Login successfull.";
        }
        return "Invalid credentials.";
    }*/

    public ResponseEntity<RegisterRequest> register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterRequest("User Already Present."));
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new RegisterRequest("User Created Successfully."));
    }

    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Invalid credentials"));
        }
    }


    @Override
    public Boolean isUserLoggedIn(String username) {
        return loggedUsers.contains(username);
    }
}
