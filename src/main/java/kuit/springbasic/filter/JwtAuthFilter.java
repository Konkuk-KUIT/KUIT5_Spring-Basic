package kuit.springbasic.filter;


import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;

public abstract class JwtAuthFilter extends AuthFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtTokenProvider jwtTokenProvider;

    protected JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {
        String token = resolveToken(request);

        if (token == null)
            return false;

        String loginUserId = jwtTokenProvider.validateToken(token);
        if (loginUserId == null)
            return false;

        request.setAttribute("userId", loginUserId);

        return true;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

}
