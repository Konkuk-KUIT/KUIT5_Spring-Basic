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

        // 1. URL 파라미터 정보 추출
        String userId = request.getParameter("userId");
        String loginUserId = (String) request.getAttribute("loginUserId");
        // 2. 파라미터에 값이 존재하지 않으면 redirect
        if (!userId.equals(loginUserId)) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}