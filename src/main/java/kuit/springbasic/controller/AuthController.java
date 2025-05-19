package kuit.springbasic.controller;

import kuit.springbasic.auth.JwtInfo;
import kuit.springbasic.auth.JwtTokenProvider;
import kuit.springbasic.domain.User;
import kuit.springbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    // jwt를 얻는 api
    @GetMapping("/getToken")
    public JwtInfo getToken() {
        User kuit = userService.findById("kuit");
        return jwtTokenProvider.createToken(kuit.getEmail(), kuit.getUserId());
    }

    // 일반 인가 범위
    @GetMapping("/auth")
    public String auth() {
        return "ok";
    }

    // 인터셉터를 통한 loginUserId
    // 응답 결과로 "ok, loginUserId = kuit" 이 나와야 함
    @GetMapping("/auth/userId")
    public String authUserId(@RequestAttribute("loginUserId") String loginUserId) {
        return "ok, loginUserId = " + loginUserId;
    }
}
