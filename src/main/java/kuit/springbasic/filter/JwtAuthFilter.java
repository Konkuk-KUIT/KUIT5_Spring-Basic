package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;
import org.springframework.util.StringUtils;

public class JwtAuthFilter extends AuthFilter {

    private static final String BEARER_PRIFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {

        String token = resolveToken(request);

        if (!StringUtils.hasText(token)) {
            return false;
        }
            // 토큰에서 사용자 ID 추출
            String loginUserId = jwtTokenProvider.validateToken(token);
            request.setAttribute("loginUserId", loginUserId);
            return true;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PRIFIX)) {
            return bearerToken.substring(BEARER_PRIFIX.length());
        }
        return null;
    }

}
