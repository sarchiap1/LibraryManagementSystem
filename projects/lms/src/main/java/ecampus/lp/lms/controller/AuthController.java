package ecampus.lp.lms.controller;

import ecampus.lp.lms.model.User;
import ecampus.lp.lms.dto.*;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ecampus.lp.lms.repository.IUserRepository;

@RestController
@RequestMapping(value="/api")
public class AuthController {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(IUserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value="/echo")
    public String echo(){
        return "echo";
    }
    
    @PostMapping(value = "/register")
    public UserRegisterResponse register(@RequestBody UserRegisterRequest request){
        if(!Objects.equals(request.password(), request.passwordConfirm()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password do not match");

        var user = userRepository.save(
            User.of(request.firstName(), 
                    request.lastName(),
                    request.email(),
                    passwordEncoder.encode(request.password()))
        );

       return new UserRegisterResponse(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail());
    }
}
