package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class JwtAuthFilter extends AuthFilter {
    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {
        String userId;
        String token = resolveToken(request);

        if (token == null){
            return false;
        }
        userId = jwtTokenProvider.validateToken(token);

        if (userId == null){
            return false;
        }
        request.setAttribute("loginUserId", userId);

        return true;
    }
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) &&
                bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7); //"Bearer " 를 잘라냄
        }
        return null;
    }
}
