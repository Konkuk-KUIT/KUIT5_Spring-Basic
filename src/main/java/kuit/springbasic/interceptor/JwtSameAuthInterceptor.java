package kuit.springbasic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtSameAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String loginUserId = request.getParameter("loginUserId"); // JwtAuthFilter에서 설정한 값
        String userId = request.getParameter("userId");           // 사용자가 요청한 userId

        if (loginUserId == null || !loginUserId.equals(userId)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"Forbidden: userId mismatch\"}");
            return false; // 컨트롤러로 진입하지 않음
        }

        return true; // 통과
    }
}

