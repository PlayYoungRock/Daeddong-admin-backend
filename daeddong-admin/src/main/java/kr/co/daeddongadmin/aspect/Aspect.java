package kr.co.daeddongadmin.aspect;

import kr.co.daeddongadmin.common.CommonUtil;
import kr.co.daeddongadmin.exception.CustomException;
import kr.co.daeddongadmin.jwt.JwtTokenProvider;
import org.apache.poi.util.StringUtil;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Before("(@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping))" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping))" +
            "@annotation(org.springframework.web.bind.annotation.PatchMapping))" +
            "&& !execution(* kr.co.daeddongadmin.admin.controller.AdminController.login())")
    public void checkJwtToken() {
        String token = CommonUtil.resolveToken(request);
        if(StringUtil.isNotBlank(token) && jwtTokenProvider.validateToken(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            throw new CustomException("토큰이 없습니다.", "8000");
        }

    }
}
