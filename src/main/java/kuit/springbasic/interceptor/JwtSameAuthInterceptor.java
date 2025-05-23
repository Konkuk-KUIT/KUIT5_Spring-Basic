package kuit.springbasic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.auth.JwtTokenProvider;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;



@RequiredArgsConstructor
public class JwtSameAuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. URL 파라미터 정보 추출
        String userId =(String)request.getAttribute("loginUserId");

        // 2. 파라미터에 값이 존재하지 않으면 redirect
        if (!StringUtils.hasText(userId)) {
            response.sendRedirect("/");
            return false;
        }

        // 3. 토큰에서 현재 로그인된 사용자를 가져옴
        String bearerToken = request.getHeader("Authorization");
        String tokenUserId = tokenProvider.validateToken(bearerToken.substring(7));

        // 4. 모든 검증을 통과하면 요청을 계속 진행
        return tokenUserId.equals(userId);
    }
}
