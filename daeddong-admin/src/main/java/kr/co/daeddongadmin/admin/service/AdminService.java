package kr.co.daeddongadmin.admin.service;

import kr.co.daeddongadmin.admin.domain.JwtToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminService {
    JwtToken signIn(String username, String password);
}
