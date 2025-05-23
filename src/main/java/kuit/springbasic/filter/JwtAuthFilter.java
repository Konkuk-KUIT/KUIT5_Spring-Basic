package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class JwtAuthFilter extends AuthFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {
        String token = resolveToken(request);

        if (token != null) {
            try {
                String loginUserId = jwtTokenProvider.validateToken(token);
                request.setAttribute("loginUserId", loginUserId);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
