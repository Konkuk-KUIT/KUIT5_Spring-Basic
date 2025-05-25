package kuit.springbasic.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.util.UserSessionUtils;

public class SessionAuthFilter extends AuthFilter{
    @Override
    protected boolean isAuthenticated(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return UserSessionUtils.isLoggedIn(session);
    }
}
