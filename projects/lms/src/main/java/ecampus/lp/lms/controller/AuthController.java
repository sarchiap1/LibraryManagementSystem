package ecampus.lp.lms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class AuthController {
    @GetMapping(value="/echo")
    public String echo(){
        return "echo";
    }
}
