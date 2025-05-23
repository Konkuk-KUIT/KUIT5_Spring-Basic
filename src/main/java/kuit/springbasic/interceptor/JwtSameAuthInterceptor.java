package kuit.springbasic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtSameAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getParameter("userId");
        String loginUserId = (String) request.getAttribute("loginUserId");

        if (!userId.equals(loginUserId)) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
