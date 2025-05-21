package kuit.springbasic.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null) {
            try {
                // validateToken()은 유효성 검사 + userId 반환
                String loginUserId = jwtTokenProvider.validateToken(token);

                // loginUserId를 request attribute에 저장
                request.setAttribute("loginUserId", loginUserId);
            } catch (Exception e) {
                // 만료되거나 위조된 토큰 → JwtExceptionFilter가 처리함
                // 예외를 던지면 JwtExceptionFilter에서 캐치 가능
                throw e;
            }
        }

        // 다음 필터로 넘김
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
