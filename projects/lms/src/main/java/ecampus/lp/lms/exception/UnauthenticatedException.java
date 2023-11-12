package ecampus.lp.lms.exception;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

public class UnauthenticatedException extends ResponseStatusException{
    public UnauthenticatedException(){
        super(HttpStatus.UNAUTHORIZED,"Unhautorized");
    }
}
