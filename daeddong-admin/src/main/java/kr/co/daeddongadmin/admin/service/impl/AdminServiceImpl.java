package kr.co.daeddongadmin.admin.service.impl;

import kr.co.daeddongadmin.admin.domain.JwtToken;
import kr.co.daeddongadmin.admin.repository.AdminRepository;
import kr.co.daeddongadmin.admin.service.AdminService;
import kr.co.daeddongadmin.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public JwtToken signIn(String username, String password) {
		// 1. username + password 를 기반으로 Authentication 객체 생성
		// 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

		// 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
		// authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// 3. 인증 정보를 기반으로 JWT 토큰 생성
		JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

		return jwtToken;
	}

}
