package kr.co.daeddongadmin.auth.service;

import kr.co.daeddongadmin.auth.domain.Auth;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface AuthService {
    public UserDetails loadUserByUsername(String userName, String password);

}
