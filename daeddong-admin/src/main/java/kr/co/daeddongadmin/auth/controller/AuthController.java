package kr.co.daeddongadmin.auth.controller;

import kr.co.daeddongadmin.auth.domain.Auth;
import kr.co.daeddongadmin.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Controller
public class AuthController {
    @Autowired
    private AuthService authService;
    private final String jwtSecret = "H13o8i21vDgluwAYNKVGskC1tZjo88ZsDHbwucALrrMVHLQ1N7/ePfRFVXnAFKxzWW5rbxOB5Q5yW/Wt+Cgrqw==";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Auth auth) {
        try {
            UserDetails userDetails = authService.loadUserByUsername(auth.getUsername(),auth.getPassword());
            String token = Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
            System.out.println("token = " + token);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            return ResponseEntity.ok().headers(headers).body("Login successful");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
