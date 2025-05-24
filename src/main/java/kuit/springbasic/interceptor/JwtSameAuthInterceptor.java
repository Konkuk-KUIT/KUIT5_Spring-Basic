package kuit.springbasic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtSameAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getParameter("userId");
        String loginUserId = request.getParameter("loginUserId");

        if (!StringUtils.hasText(userId) || !StringUtils.hasText(loginUserId)) {
            response.sendRedirect("/");
            return false;
        }

        return loginUserId.equals(userId);
    }
}
