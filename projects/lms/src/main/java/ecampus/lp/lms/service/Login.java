package ecampus.lp.lms.service;

import lombok.Getter;

/**
 * Responsibility to create and manage Token
 */
public class Login {
    @Getter
    private final Token accessToken;

    @Getter
    private final Token refreshToken;

    private Login(Token accessToken, Token refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    
    public static Login of(Long userId, String accessSecret, String refreshSecret){
        return new Login(
            Token.of(userId, 10L, accessSecret),
            Token.of(userId, 1440L, refreshSecret)
        );
    }

}
