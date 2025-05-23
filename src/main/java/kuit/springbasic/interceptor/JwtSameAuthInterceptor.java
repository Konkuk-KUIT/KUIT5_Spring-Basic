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
        String userId = request.getParameter("userId");                 // 요청 파라미터
        String loginUserId = (String) request.getAttribute("loginUserId"); // 필터가 넣어준 userId

        // 파라미터가 없으면 실패
        if (!StringUtils.hasText(userId)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing userId in request");
            return false;
        }

        // 불일치하면 실패
        if (!userId.equals(loginUserId)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("JWT userId mismatch");
            return false;
        }

        return true;
    }
}
