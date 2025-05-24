package kuit.springbasic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SameUserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. URL 파라미터 정보 추출
        String userId = request.getParameter("userId");

        // 2. 파라미터에 값이 존재하지 않으면 redirect
        if (!StringUtils.hasText(userId)) {
            response.sendRedirect("/");
            return false;
        }

        // 3. 세션에서 현재 로그인된 사용자를 가져옴
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(UserSessionUtils.USER_SESSION_KEY);

        // 4. 모든 검증을 통과하면 요청을 계속 진행
        return user.getUserId().equals(userId);
    }
}
