package kuit.springbasic.controller;

import kuit.springbasic.auth.JwtInfo;
import kuit.springbasic.auth.JwtTokenProvider;
import kuit.springbasic.domain.User;
import kuit.springbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    // @RequestParam -> @RequestAttribute 으로 변경
    public String authUserId(@RequestAttribute("loginUserId") String loginUserId) {
        return "ok, loginUserId = " + loginUserId;
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshHeader) {
        if(refreshHeader == null || !refreshHeader.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("The authorization header is missing or malformed.");
        }

        String refreshToken = refreshHeader.substring(7);

        try {
            String userId = jwtTokenProvider.validateRefreshToken(refreshToken);
            User user = userService.findById(userId);
            if(user == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("User not exist.");
            }
            // New Token
            JwtInfo newToken = jwtTokenProvider.createToken(user.getEmail(), user.getUserId());
            return ResponseEntity.ok(newToken);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Refresh Token");
        }
    }
}
