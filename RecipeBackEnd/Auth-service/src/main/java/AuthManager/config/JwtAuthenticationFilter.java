package AuthManager.config;

import AuthManager.entity.User;
import AuthManager.rest.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //get the token from header
        String token = getToken(request);
        //check the token either valid or not
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            String name = jwtTokenProvider.getUsername(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(name);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        //load the user and setAuthentication
        filterChain.doFilter(request, response);
    }
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")){
            return token.substring(7,token.length());
        }
        return null;
    }

}
