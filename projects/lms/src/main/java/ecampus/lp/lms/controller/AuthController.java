package ecampus.lp.lms.controller;

import ecampus.lp.lms.model.User;
import ecampus.lp.lms.dto.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecampus.lp.lms.repository.IUserRepository;

@RestController
@RequestMapping(value="/api")
public class AuthController {

    private final IUserRepository userRepository;

    public AuthController(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping(value="/echo")
    public String echo(){
        return "echo";
    }
    
    @PostMapping(value = "/register")
    public UserRegisterResponse register(@RequestBody UserRegisterRequest request){
        var user = userRepository.save(
            User.of(request.firstName(), 
                    request.lastName(),
                    request.email(),
                    request.password())
        );

       return new UserRegisterResponse(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail());
    }
}
