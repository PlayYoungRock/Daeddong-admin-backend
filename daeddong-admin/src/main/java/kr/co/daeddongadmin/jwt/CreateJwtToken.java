package kr.co.daeddongadmin.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
public class CreateJwtToken {
    private final String jwtSecret = "H13o8i21vDgluwAYNKVGskC1tZjo88ZsDHbwucALrrMVHLQ1N7/ePfRFVXnAFKxzWW5rbxOB5Q5yW/Wt+Cgrqw==";

    private int jwtExpirationMs = 3600000; // 1시간

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
