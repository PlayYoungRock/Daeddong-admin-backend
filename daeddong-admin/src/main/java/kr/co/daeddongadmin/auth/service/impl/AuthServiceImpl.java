package kr.co.daeddongadmin.auth.service.impl;

import kr.co.daeddongadmin.auth.domain.Auth;
import kr.co.daeddongadmin.auth.repository.AuthRepository;
import kr.co.daeddongadmin.auth.service.AuthService;
import kr.co.daeddongadmin.config.InvalidPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthRepository authRepository;

	/**
	 * 진행중인 이벤트 리스트
	 */
	@Override
	public UserDetails loadUserByUsername(String userName,String password) throws UsernameNotFoundException {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!"admin".equals(userName)) {
			throw new UsernameNotFoundException("User not found with username: " + userName);
		}
		String expectedPassword = "123";
		String encodedPassword = passwordEncoder.encode(expectedPassword); // 비밀번호 암호화

		if (!passwordEncoder.matches(password, encodedPassword)) {
			throw new InvalidPasswordException("Inva1lid password");
		}

		return org.springframework.security.core.userdetails.User.builder()
				.username(userName)
				.password(encodedPassword)
				.roles("USER")
				.build();
	}

}
