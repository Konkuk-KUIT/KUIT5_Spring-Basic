package kuit.springbasic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JwtSameAuthInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginUserId = (String) request.getAttribute("loginUserId");
        String userId = request.getParameter("userId");

        System.out.println("loginUserId:"+loginUserId);
        System.out.println("userId:"+userId);

        if(loginUserId == null || loginUserId.isBlank()
                || userId == null || userId.isBlank()
                || !loginUserId.equals(userId)) {
            setResponse(response, "User Mismatch");
            return false;
        }
        return true;
    }

    private void setResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        objectMapper.writeValue(response.getWriter(), message);
    }
}
