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
        Object loginUserObject = request.getAttribute("userId");
        String userId = request.getParameter("userId");

        if (loginUserObject == null || !StringUtils.hasText(userId)) {
            response.sendRedirect("/");
            return false;
        }

        String loginUserId = (String) loginUserObject;
        if (!loginUserId.equals(userId)) {
            response.sendRedirect("/");

            return false;
        }

        return true;
    }
}
