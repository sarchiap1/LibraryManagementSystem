package ecampus.lp.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Record is a special purpose class in Java that is designed to provide an efficient and easy way for programmers to carry aggregate data
 * 
 */

public record UserRegisterRequest(
        @JsonProperty("firstName") String firstName, 
        String lastName, 
        String email, 
        String password, 
        String passwordConfirm){}
    
