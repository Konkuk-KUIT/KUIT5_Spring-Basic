package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;

public class JwtAuthFilter extends AuthFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {
        String token = resolveToken(request);

        if (token != null) {
            String loginUserID = jwtTokenProvider.validateToken(token);
            request.setAttribute("loginUserId", loginUserID);

            return true;
        }
        return false;
    }

    private String resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
