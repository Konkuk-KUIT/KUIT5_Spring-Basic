package kuit.springbasic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtSameAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 요청 쿼리 스트링에서 userId 추출
        String userIdParam = request.getParameter("userId");

        // JWT 인증 필터에서 세팅한 사용자 ID 추출
        String loginUserIdAttr = (String) request.getAttribute("loginUserId");

        // 둘 중 하나라도 비어 있으면 인증 실패
        if (!StringUtils.hasText(userIdParam) || !StringUtils.hasText(loginUserIdAttr)) {
            response.sendRedirect("/");
            return false;
        }

        // 요청 ID와 로그인 ID가 불일치하면 요청 거부
        if (!userIdParam.equals(loginUserIdAttr)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다.");
            return false;
        }

        // 모든 조건 통과
        return true;
    }
}
