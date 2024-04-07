package kr.co.daeddongadmin.aspect;

import kr.co.daeddongadmin.common.CommonUtil;
import kr.co.daeddongadmin.jwt.JwtAuthenticationFilter;
import kr.co.daeddongadmin.jwt.JwtTokenProvider;
import org.apache.poi.util.StringUtil;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void checkJwtToken() {
        String token = CommonUtil.resolveToken(request);

        if(StringUtil.isNotBlank(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            throw new IllegalArgumentException("토큰 없음");
        }

    }
}
