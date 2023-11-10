package ecampus.lp.lms.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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
        var key = Jwts.SIG.HS256.key().build();
        var issueDate = Instant.now();
        var token = new Token(
            Jwts.builder()
            .claim("user_id",userId)
            .issuedAt(Date.from(issueDate))
            .expiration(Date.from(issueDate.plus(validityInMinutes, ChronoUnit.MINUTES)))
            .signWith(key)
            .compact()
        );

        return token;
    }
}
