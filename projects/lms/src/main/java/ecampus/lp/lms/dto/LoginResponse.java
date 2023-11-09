package ecampus.lp.lms.dto;

public record LoginResponse(
    Long id,
    String firstName,
    String lastName,
    String email
){}
