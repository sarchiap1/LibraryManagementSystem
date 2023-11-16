package ecampus.lp.lms.controller;

import ecampus.lp.lms.dto.*;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
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

import org.springframework.core.env.Environment;

@RestController
@RequestMapping(value="/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(IUserRepository userRepository, PasswordEncoder passwordEncoder, Environment env){
        var a = env.getProperty("application.security.access-token-secret");
        var r = env.getProperty("application.security.refresh-token-secret");
        this.authService = new AuthService(userRepository,
                                             passwordEncoder,
                                            a,
                                            r );
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
        //cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return new LoginResponse(login.getAccessToken().getToken());

        //Authentication authentication = this.
    }

    @GetMapping(value="/user")
    public UserResponse user(HttpServletRequest request){

        //return new UserResponse(0L, "non capisco", "non capisco", "non capisco");
        
        var userId = (Long)request.getAttribute("user_id");

        var user = authService.GetUser(userId);

        if(user.isEmpty())
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find user");

        return new UserResponse(user.get().getId(), user.get().getFirstName(), user.get().getLastName(), user.get().getEmail());
    }

    @PostMapping(value = "/refresh")
    public RefreshResponse refresh(@CookieValue("refresh_token") String refreshToken)
    {
        var t = authService
                    .refreshAccess(refreshToken)
                    .getAccessToken()
                    .getToken();

        var result = new RefreshResponse(t);

        return result;
    }

    @PostMapping("/logout")
    public LogoutResponse logout(HttpServletResponse response)
    {
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new LogoutResponse("success");
    }
}
