package ecampus.lp.lms.identity;

import org.springframework.web.filter.OncePerRequestFilter;

import ecampus.lp.lms.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.String;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final AuthService authService;

    public JwtAuthenticationFilter(AuthService authService){
        super();
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {
        
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }   

        request.setAttribute("user", authService.getUserFromToken(authorizationHeader.substring(7)));

    }
}

    
