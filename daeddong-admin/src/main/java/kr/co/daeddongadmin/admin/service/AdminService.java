package kr.co.daeddongadmin.admin.service;

import kr.co.daeddongadmin.admin.domain.JwtToken;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    JwtToken signIn(String username, String password, HttpServletRequest request);
}
