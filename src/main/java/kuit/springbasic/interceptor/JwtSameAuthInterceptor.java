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

        // 1. URL 파라미터 정보 추출
        String userId = request.getParameter("userId");
        String loginUserId = request.getParameter("loginUserId");//...?

        // 2. 파라미터에 값이 존재하지 않으면 redirect
        if (!StringUtils.hasText(userId) || !StringUtils.hasText(loginUserId)) {
            response.sendRedirect("/");
            return false;
        }

        // 4. 모든 검증을 통과하면 요청을 계속 진행
        return loginUserId.equals(userId);
    }
}
