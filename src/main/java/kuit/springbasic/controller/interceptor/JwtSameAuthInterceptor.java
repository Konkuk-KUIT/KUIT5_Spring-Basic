package kuit.springbasic.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtSameAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 사용자는 URL로 id전송
        String userId = request.getParameter("userId");
        String loginUserId = (String) request.getAttribute("loginUserId");
        //파라미터 값이 존재하지 않으면 리다이렉트
        if(!StringUtils.hasText(userId)){
            response.sendRedirect("redirect:/");
            return false;
        }
        // 세션에서 현재 로그인 된 사용자를 가져옴
        if(!userId.equals(loginUserId)){
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }
}
