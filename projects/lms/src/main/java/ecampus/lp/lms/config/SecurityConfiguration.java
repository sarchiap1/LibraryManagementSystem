package ecampus.lp.lms.config;

import ecampus.lp.lms.identity.*;
import ecampus.lp.lms.service.AuthService;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration {
 
    @Autowired
    private AuthService authService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /* 
        http
                .authorizeHttpRequests((authorize) ->
                        authorize.anyRequest().authenticated()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/welcome")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        */
        // https://docs.spring.io/spring-security/reference/6.0-SNAPSHOT/servlet/authorization/authorize-http-requests.html
        http
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/api/register").permitAll() 
            .requestMatchers("/api/login").permitAll()
            .requestMatchers("/api/refresh").permitAll()
            .requestMatchers("/api/echo").permitAll()
            .anyRequest()
            .authenticated()
        );
    
        // https://docs.spring.io/spring-security/reference/reactive/exploits/csrf.html
        http.csrf(csrf->csrf.disable());

        http.addFilterAfter(new JwtAuthenticationFilter(authService), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                configuration.setAllowedMethods(Arrays.asList("GET","POST"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

    /* 
    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
    */
}


