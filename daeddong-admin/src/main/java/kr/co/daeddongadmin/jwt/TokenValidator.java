package kr.co.daeddongadmin.jwt;

import org.springframework.stereotype.Component;

@Component
public class TokenValidator {

    // 토큰의 유효성을 검사하는 메소드
    public boolean validateToken(String token) {
        // 여기에 토큰 유효성 검사 로직을 추가합니다.
        // 예를 들어, 토큰 디코딩, 만료 시간 확인, 서명 검증 등의 작업이 이루어집니다.

        // 간단한 예제로서, 토큰이 null이 아니고 비어있지 않다면 유효한 토큰으로 간주합니다.
        return token != null && !token.isEmpty();
    }
}
