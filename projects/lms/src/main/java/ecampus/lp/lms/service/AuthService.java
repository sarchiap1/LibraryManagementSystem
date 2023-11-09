package ecampus.lp.lms.service;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ecampus.lp.lms.model.User;
import ecampus.lp.lms.repository.IUserRepository;

@Service
public class AuthService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(IUserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String firstName, String lastName, String email, String password, String passwordConfirm)
    {
        if(!Objects.equals(password, passwordConfirm))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password do not match confirm");
        
        return userRepository.save(
            User.of(firstName, lastName, email, passwordEncoder.encode(passwordConfirm))
        );
    }

    public User login(String email, String password){
        // find user by email
        var user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials"));

        // see if password match
        if(!passwordEncoder.matches(password, user.getPassword())) // The encoder can generate different encoded of the same password so we need matches method
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials");

        // return user
        return user;
    }
}
