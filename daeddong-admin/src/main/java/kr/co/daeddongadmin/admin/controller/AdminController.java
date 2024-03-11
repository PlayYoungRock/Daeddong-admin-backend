package kr.co.daeddongadmin.admin.controller;

import kr.co.daeddongadmin.admin.domain.Admin;
import kr.co.daeddongadmin.admin.domain.JwtToken;
import kr.co.daeddongadmin.admin.service.AdminService;
import kr.co.daeddongadmin.jwt.CreateJwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CreateJwtToken createJwtToken;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Admin admin) {
        try {
            HttpHeaders headers = new HttpHeaders();
            JwtToken jwtToken = adminService.signIn(admin.getUsername(), admin.getPassword());
            log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
            headers.add("Authorization", "Bearer " + jwtToken);
            return ResponseEntity.ok().headers(headers).body("Login successful");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }


}
