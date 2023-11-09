package ecampus.lp.lms.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.security.Key;
import java.sql.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.val;

public class Token {
    @Getter
    private final String token;

    private Token(String token)
    {

        this.token = token;
    }

    public static Token of(Long userId, Long validityInMinutes, String secretKey){
        var issueDate = Instant.now();
        var token = Jwts.builder()
            .claim("user_id",userId)
            .issuedAt(Date.from(issueDate))
            .expiration(Date.from(issueDate.plus(validityInMinutes, ChronoUnit.MINUTES)))
            .signWith(null)
            .compact();

        return token;
    }
}
