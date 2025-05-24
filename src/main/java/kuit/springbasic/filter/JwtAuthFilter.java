package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class JwtAuthFilter extends AuthFilter{

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {

        System.out.println("authenticate: " + request.getRequestURI());

        if (request.getRequestURI().contains("/loginForm")) {
            return true;  // 다시 loginForm으로 가는 건 무시하고 통과
        }

        String token = resolveToken(request);
        if(token == null){
            return false;
        }

        String loginUserId = jwtTokenProvider.validateToken(token);

        if(loginUserId != null){
            System.out.println("return true");
            request.setAttribute("loginUserId", loginUserId);
            return true;
        }

        System.out.println("return false");
        return false;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
