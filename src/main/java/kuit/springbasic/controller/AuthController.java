package kuit.springbasic.controller;

import kuit.springbasic.auth.JwtInfo;
import kuit.springbasic.auth.JwtTokenProvider;
import kuit.springbasic.domain.User;
import kuit.springbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping("/getToken")
    public JwtInfo getToken() {
        User kuit = userService.findById("kuit");
        return jwtTokenProvider.createToken(kuit.getEmail(), kuit.getUserId());
    }

    @GetMapping("/auth")
    public String auth() {
        return "ok";
    }

    @GetMapping("/auth/userId")
    public String authUserId(@RequestParam("loginUserId") String loginUserId) {
        return "ok" + loginUserId;
    }
}
