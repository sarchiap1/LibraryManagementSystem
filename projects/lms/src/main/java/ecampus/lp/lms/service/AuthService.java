package ecampus.lp.lms.service;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.beans.factory.annotation.Value;

import ecampus.lp.lms.model.User;
import ecampus.lp.lms.repository.IUserRepository;

@Service
public class AuthService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String accessTokenSecret;
    private final String refreshTokenSecret;

    public AuthService(
            IUserRepository userRepository, 
            PasswordEncoder passwordEncoder,
            @Value("${application.security.access-token-secret}") String accessTokenSecret,
            @Value("${application.security.refresh-token-secret}") String refreshTokenSecret
        ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        this.accessTokenSecret = accessTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
    }

    public User register(String firstName, String lastName, String email, String password, String passwordConfirm)
    {
        if(!Objects.equals(password, passwordConfirm))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password do not match confirm");
        
        return userRepository.save(
            User.of(firstName, lastName, email, passwordEncoder.encode(passwordConfirm))
        );
    }

    public Login login(String email, String password){
        // find user by email
        var user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials"));

        // see if password match
        if(!passwordEncoder.matches(password, user.getPassword())) // The encoder can generate different encoded of the same password so we need matches method
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials");

        // return 
        return Login.of(user.getId(), accessTokenSecret, refreshTokenSecret);
    }
}
