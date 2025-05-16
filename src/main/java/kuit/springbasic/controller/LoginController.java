package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;

    /**
     * TODO: showLoginForm
     */
    @GetMapping("/user/loginForm")
    public String showLoginForm() {
        return "/user/login";
    }


    /**
     * TODO: showLoginFailed
     */
    @GetMapping("/user/loginFailed")
    public String showLoginFailed() {
        return "/user/loginFailed";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */

    //loginV1 : @RequestParam("")
    @PostMapping("/user/login")
    public String loginV1(@RequestParam("userId") String userId,
                          @RequestParam("password") String password,
                          HttpServletRequest request) {
        return tryLogin(userId, password, request);
    }

    // loginV2 : @RequestParam
    @PostMapping("/user/login/v2")
    public String loginV2(@RequestParam String userId,
                          @RequestParam String password,
                          HttpServletRequest request) {
        return tryLogin(userId, password, request);
    }

    // loginV3 : @RequestParam 생략(비추천)
    @PostMapping("/user/login/v3")
    public String loginV3(String userId,
                          String password,
                          HttpServletRequest request) {
        return tryLogin(userId, password, request);
    }

    // loginV4 : @ModelAttribute
    @PostMapping("/user/login/v4")
    public String loginV4(@ModelAttribute User user,
                          HttpServletRequest request) {
        return tryLogin(user.getUserId(), user.getPassword(), request);
    }

    private String tryLogin(String userId, String password, HttpServletRequest request) {
        User findUser = userRepository.findByUserId(userId);
        if (findUser != null && findUser.isSameUser(new User(userId, password))) {
            request.getSession().setAttribute("user", findUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }


    /**
     * TODO: logout
     */
    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

}
