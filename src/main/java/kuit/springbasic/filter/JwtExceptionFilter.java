package kuit.springbasic.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();  // JSON 응답 생성을 위한 객체

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);  // 다음 필터 또는 컨트롤러로 요청 전달
        } catch (ExpiredJwtException ex) {
            handleExpiredToken(response);  // 만료된 토큰 처리
        }
    }

    private void handleExpiredToken(HttpServletResponse response) throws IOException {
        // 응답의 콘텐츠 타입과 상태 코드 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // 클라이언트에 반환할 메시지 작성
        String errorMessage = objectMapper.writeValueAsString("Access denied: token has expired");

        response.getWriter().write(errorMessage);  // 메시지를 응답에 작성
    }
}
