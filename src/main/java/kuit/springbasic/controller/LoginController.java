package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    @GetMapping("/loginForm")
    public String showLoginForm() {
        return "user/login";
    }

    @GetMapping("/loginFailed")
    public String showLoginFailed() {
        return "user/loginFailed";
    }

    //    @PostMapping("/login")
    public String loginV1(@RequestParam("userId") String userId,
                          @RequestParam("password") String password,
                          HttpServletRequest request) {
        User findUser = userRepository.findByUserId(userId);
        User loginUser = new User(userId, password);

        if (findUser != null && loginUser.isSameUser(findUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loginUser);

            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    //    @PostMapping("/login")
    public String loginV2(@RequestParam String userId,
                          @RequestParam String password,
                          HttpServletRequest request) { // @RequestParam의 Key값과 변수명이 동일하다면, name을 명시하지 않아도 됨
        User findUser = userRepository.findByUserId(userId);
        User loginUser = new User(userId, password);

        if (findUser != null && loginUser.isSameUser(findUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loginUser);

            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    //    @PostMapping("/login")
    public String loginV3(String userId,
                          String password,
                          HttpServletRequest request) { // Key값과 변수명이 동일하면, @RequestParam 생략 가능
        User findUser = userRepository.findByUserId(userId);
        User loginUser = new User(userId, password);

        if (findUser != null && loginUser.isSameUser(findUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loginUser);

            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @PostMapping("/login")
    public String loginV4(@ModelAttribute User loginUser,
                          HttpServletRequest request) { // @ModelAttribute를 이용하여 전달받은 값으로 바로 User 객체 생성
        User user = userRepository.findByUserId(loginUser.getUserId());

        if (user != null && user.isSameUser(loginUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loginUser);

            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(USER_SESSION_KEY);

        return "redirect:/";
    }
}
