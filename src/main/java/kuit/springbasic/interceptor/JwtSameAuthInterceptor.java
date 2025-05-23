package kuit.springbasic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtSameAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginUserId = (String) request.getAttribute("loginUserId");
        String userId = request.getParameter("userId");

        if (loginUserId == null || userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 정보가 부족합니다.");
            return false;
        }

        if (!loginUserId.equals(userId)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "자신의 정보만 접근할 수 있습니다.");
            return false;
        }

        return true;
    }
}
