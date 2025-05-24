package kuit.springbasic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtSameAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String[] parts = requestURI.split("/");

        String userIdFromPath = parts[parts.length - 1];

        String loginUserId = (String) request.getAttribute("loginUserId");

        System.out.println("JwtSameAuthInterceptor - pathUserId: " + userIdFromPath + ", loginUserId: " + loginUserId);

        if (loginUserId == null || !loginUserId.equals(userIdFromPath)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("인증된 사용자만 접근 가능합니다.");
            return false;
        }

        return true;
    }

}
