package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends AuthFilter{
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {
        String token = resolveToken(request);
        if(token == null) {
            return false;
        }

        String loginUserId = jwtTokenProvider.validateToken(token);
        request.setAttribute("loginUserId", loginUserId);
        return true;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
