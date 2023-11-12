package ecampus.lp.lms.identity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import ecampus.lp.lms.service.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.String;
import java.util.ArrayList;

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

        /* 
        String accessTokenFromCookie = null;
        var cookie = WebUtils.getCookie(request, "refresh_token");
        if(cookie != null)
        {
            var refreshToken = cookie.getValue();
            accessTokenFromCookie = authService
                    .refreshAccess(refreshToken)
                    .getRefreshToken()
                    .getToken();
        }*/

        var accessToken = authorizationHeader.substring(7);

        var userId = Token.from(accessToken, authService.getAccessTokenSecret());

        request.setAttribute("user_id", userId);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,null,new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}

    
