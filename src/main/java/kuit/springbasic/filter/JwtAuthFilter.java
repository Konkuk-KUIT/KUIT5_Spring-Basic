package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.auth.JwtTokenProvider;
import org.springframework.util.StringUtils;

public class JwtAuthFilter extends AuthFilter{

    private final JwtTokenProvider jwtTokenProvider;

   public JwtAuthFilter(JwtTokenProvider jwtTokenProvider){
       this.jwtTokenProvider = jwtTokenProvider;
   }
    @Override
    protected boolean isAuthenticated(HttpServletRequest request) {
        String token = resolveToken(request);

        if (!StringUtils.hasText(token)){
            return false;
        }

        try{
            String userId = jwtTokenProvider.validateToken(token);
            request.setAttribute("loginUserId", userId);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    private String resolveToken(HttpServletRequest request){
       String bearerToken = request.getHeader("Authorization");
       if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
          return bearerToken.substring(7);
       }
       return null;
    }
}
