package kuit.springbasic.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// OncePerRequestFilter: 메인 요청에만 필터가 걸림
public abstract class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (!isAuthenticated(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            response.sendRedirect("/user/loginForm"); // 비인증 사용자면 login page 로 리다이렉트
            return;
        }

        filterChain.doFilter(request, response);
    }

    protected abstract boolean isAuthenticated(HttpServletRequest request);
}

