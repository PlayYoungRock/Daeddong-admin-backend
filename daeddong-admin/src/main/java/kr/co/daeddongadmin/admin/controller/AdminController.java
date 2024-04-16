package kr.co.daeddongadmin.admin.controller;

import kr.co.daeddongadmin.admin.domain.Admin;
import kr.co.daeddongadmin.admin.domain.JwtToken;
import kr.co.daeddongadmin.admin.service.AdminService;
import kr.co.daeddongadmin.common.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


@Controller
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Admin admin) {
        Map<String, String> response = new HashMap<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            JwtToken jwtToken = adminService.signIn(admin.getUsername(), CommonUtil.toSHA256(admin.getPassword()));
            log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
            headers.add("Authorization", "Bearer " + jwtToken);
            response.put("message", "Login successful");
            response.put("resultCode", "0000");
            response.put("accessToken", jwtToken.getAccessToken()); // AccessToken을 response에 추가
            response.put("refreshToken", jwtToken.getRefreshToken()); // RefreshToken을 response에 추가

            return ResponseEntity.ok().headers(headers).body(response);

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
