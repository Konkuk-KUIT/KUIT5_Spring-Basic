package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter extends AuthFilter{
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {
        String token = resolveToken(request);
        if (token == null) {
            return false;
        }

        try {
            String userId = jwtTokenProvider.validateToken(token);
            // request에 loginUserId를 저장 (request wrapper를 써야 안전하지만 여기선 attribute로 저장)
            request.setAttribute("loginUserId", userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거
        }
        return null;
    }
}
