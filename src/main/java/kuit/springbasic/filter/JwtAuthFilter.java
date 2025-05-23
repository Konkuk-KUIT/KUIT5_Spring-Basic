package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class JwtAuthFilter extends AuthFilter {

    private final JwtTokenProvider tokenProvider;


    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken!=null){
            String userId = tokenProvider.validateToken(bearerToken.substring(7));
            request.setAttribute("loginUserId",userId);
            return true;
        }
        return false;
    }
}
