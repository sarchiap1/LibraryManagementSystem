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
import ecampus.lp.lms.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(IUserRepository userRepository, PasswordEncoder passwordEncoder){
        this.authService = new AuthService(userRepository, passwordEncoder,null,null);
    }

    @GetMapping(value="/echo")
    public String echo(){
        return "echo";
    }
    
    @PostMapping(value = "/register")
    public UserRegisterResponse register(@RequestBody UserRegisterRequest request){
        if(!Objects.equals(request.password(), request.passwordConfirm()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password do not match");

        var user = authService.register(
                    request.firstName(), 
                    request.lastName(),
                    request.email(),
                    request.password(),
                    request.passwordConfirm() 
        );

       return new UserRegisterResponse(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail());
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request, HttpServletResponse response){
        var login = authService.login(request.email(), request.password());

        Cookie cookie = new Cookie("refresh_token", login.getRefreshToken().getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/api");

        response.addCookie(cookie);

        return new LoginResponse(login.getAccessToken().getToken());
    }

    @GetMapping(value="/user")
    public UserResponse user(HttpServletRequest request){
        System.out.println(request);
        return null;
    }

}
