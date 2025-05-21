package kuit.springbasic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.auth.JwtTokenProvider;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class JwtReissueInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtReissueInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String refreshToken = resolveToken(request, response);
        if (refreshToken == null) return false;
        try {
            // Refresh Token 검증
            String userId = jwtTokenProvider.validateRefreshToken(refreshToken);
            request.setAttribute("loginUserId", userId);
            return true;
        } catch (Exception e) {
            System.out.println("[JwtReissueInterceptor] 토큰 검증 실패: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            return false;
        }
    }

    private static String resolveToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
