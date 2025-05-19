package kuit.springbasic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtSameAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginUserId = (String) request.getAttribute("loginUserId");
        String requestedUserId = request.getParameter("loginUserId");

        if (!StringUtils.hasText(requestedUserId) || loginUserId == null) {
            response.sendRedirect("/");
            return false;
        }

        return loginUserId.equals(requestedUserId);
    }
}
