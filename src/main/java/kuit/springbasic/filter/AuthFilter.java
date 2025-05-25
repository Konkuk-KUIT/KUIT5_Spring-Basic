package kuit.springbasic.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.util.UserSessionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public abstract class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if(!isAuthenticated(request)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            response.sendRedirect("redirect:/user/loginForm");
            return;
        }
        filterChain.doFilter(request, response);

    }
    protected abstract boolean isAuthenticated(HttpServletRequest request);
}
