package ecampus.lp.lms.dto;

public record UserRegisterResponse(
        Long id,
        String firstName, 
        String lastName, 
        String email
)
{}
