package kr.co.daeddongadmin.admin.service.impl;

import kr.co.daeddongadmin.admin.domain.Admin;
import kr.co.daeddongadmin.admin.domain.AdminLog;
import kr.co.daeddongadmin.admin.domain.JwtToken;
import kr.co.daeddongadmin.admin.repository.AdminRepository;
import kr.co.daeddongadmin.admin.service.AdminService;
import kr.co.daeddongadmin.util.CommonUtil;
import kr.co.daeddongadmin.exception.CustomException;
import kr.co.daeddongadmin.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
	public JwtToken signIn(String username, String password, HttpServletRequest request) {
		// 1. username + password 를 기반으로 Authentication 객체 생성
		// 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		Admin admin = adminRepository.selectPasswordFailCount(username);
		AdminLog adminLog = new AdminLog();
		adminLog.setLoginId(username);
		adminLog.setLoginIp(CommonUtil.getAdminIp(request));
		//현재시간 기준으로 입력한 시간과 분 차이 체크 로직
		long minutesDifference = CommonUtil.gapTime(admin.getLastLogin());
		//현재 시간이 기준 시간으로부터 마지막 로그인 일자가 5분 이상 경과하였는지,비밀번호 틀린횟수가 5회 이상인지 체크
		if (admin.getFailPasswordCnt() > 5  && minutesDifference < 6) {
			adminLog.setLoginResult("1003");
			adminLog.setLoginResultMsg("비밀번호 입력횟수 초과");
			adminRepository.insertAdminLoginLog(adminLog);
			throw new CustomException("비밀번호가 입력횟수를 초과하였습니다. 5분 뒤 다시 시도바랍니다.", "1003");
		}

		//비밀번호 일치하지 않을 경우 틀린횟수 추가 및 마지막 로그인 일자 현재일자로 저장
		if(!authenticationToken.getCredentials().equals(adminRepository.findByUsername(username).get().getPassword())){
			adminLog.setLoginResult("1002");
			adminLog.setLoginResultMsg("비밀번호 불일치");
			adminRepository.insertAdminLoginLog(adminLog);
			adminRepository.updatePasswordFailCount(username);
			throw new CustomException("비밀번호가 일치하지 않습니다.", "1002");
		}

		//로그인 성공 할 경우 마지막 로그인 일자 업데이트 및 비밀번호 실패 횟수 초기화 로직 추가
		adminLog.setLoginResult("0000");
		adminLog.setLoginResultMsg("로그인 성공");
		adminRepository.insertAdminLoginLog(adminLog);
		adminRepository.updatePasswordReset(username);

		// 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
		// authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		// 3. 인증 정보를 기반으로 JWT 토큰 생성
		JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

		return jwtToken;
	}

	@Override
	public List<Admin> selectAdminList(Map<String, Object> paramMap) {
		paramMap.put("index",Integer.parseInt(paramMap.get("index").toString()));
		paramMap.put("count",Integer.parseInt(paramMap.get("count").toString()));
		return adminRepository.selectAdminList(paramMap);
	}

	@Override
	public int selectAdminCount(Map<String, Object> paramMap) {
		return adminRepository.selectAdminCount(paramMap);
	}

	@Override
	public Admin selectAdminInfo(String id) {
		return adminRepository.selectAdminInfo(id);
	}

	@Override
	public int insertAdmin(Admin admin) {
		return adminRepository.insertAdmin(admin);
	}
	@Override
	public int updateAdmin(Admin admin) {
		return adminRepository.updateAdmin(admin);
	}

	@Override
	public int deleteAdmin(String id) {
		return adminRepository.deleteAdmin(id);
	}

}
