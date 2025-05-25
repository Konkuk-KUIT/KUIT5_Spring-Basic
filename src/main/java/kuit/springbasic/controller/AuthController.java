package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.auth.JwtInfo;
import kuit.springbasic.auth.JwtTokenProvider;
import kuit.springbasic.domain.User;
import kuit.springbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping("/getToken")
    public JwtInfo getToken(){
        User kuit = userService.findById("kuit");
        return jwtTokenProvider.createToken(kuit.getEmail(), kuit.getUserId());

    }

    @GetMapping("/auth")
    public String auth() {return "ok";}

    @GetMapping("/auth/userId")
    public String authUserId(@RequestParam("loginUserId") String loginUserId){
        return "ok, loginUserId = " + loginUserId;
    }

    @PostMapping("/token/refresh")
    public JwtInfo refreashAcessToken(HttpServletRequest request, HttpServletResponse resposne) throws Exception {
        String refreshToken = resolveToken(request);
        if(!StringUtils.hasText(refreshToken)){
            throw new Exception("Refresh Token이 필요합니다.");
        }
        try{
            String userId = jwtTokenProvider.validateToken(refreshToken);
            User user = userService.findById(userId);
            if (user == null) {
                throw new Exception("사용자를 찾을 수 없습니다.");
            }
            return jwtTokenProvider.createToken(user.getEmail(),user.getUserId());
        } catch(Exception e){
            throw new Exception("유효하지 않은 Refresh Token입니다.");
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
