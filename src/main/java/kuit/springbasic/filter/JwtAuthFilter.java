package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;

public class JwtAuthFilter extends AuthFilter{

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }

        // Bearer 뒤에 오는 토큰값
        String token = authHeader.substring(7);
        String userId = jwtTokenProvider.validateToken(token);
        request.setAttribute("loginUserId", userId);

        return true;
    }
}
