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
        String userId = request.getParameter("loginUserId");
        String loginUserId = (String) request.getAttribute("loginUserId");

        if (!StringUtils.hasText(loginUserId) || userId == null) {
            return false;
        }

        return true;
    }
}
