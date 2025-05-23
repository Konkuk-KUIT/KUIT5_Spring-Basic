package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;
import org.springframework.util.StringUtils;

public class JwtAuthFilter extends AuthFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {
        String token = extractToken(request);

        if (!StringUtils.hasText(token)) {
            return false; // 토큰이 없으면 인증 실패
        }

        try {
            String userId = jwtTokenProvider.validateToken(token); // 토큰 유효성 검사 및 사용자 ID 추출
            request.setAttribute("loginUserId", userId); // request에 사용자 ID 속성 추가
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(TOKEN_PREFIX)) {
            return authHeader.substring(TOKEN_PREFIX.length());
        }

        return null; // 유효하지 않은 형식이면 null 반환
    }
}

