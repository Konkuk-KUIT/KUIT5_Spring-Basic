package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.domain.User;
import kuit.springbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    /**
     * TODO: showLoginForm
     */
    @RequestMapping("/user/loginForm")
    public String showLoginForm() {
        return "user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/user/loginFailed")
    public String showLoginFailed() {
        return "user/loginFailed";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    @RequestMapping("/user/login")
    public String loginV1(@RequestParam("userId") String userId,
                          @RequestParam("password") String password,
                          HttpSession session) {
        User findUser = userService.findByIdAndPassword(userId, password);
        if (findUser == null) {
            return "redirect:/user/loginFailed";
        }

        session.setAttribute("user", findUser);
        return "redirect:/";
    }

    @RequestMapping("/user/loginV2")
    public String loginV2(@RequestParam String userId,
                          @RequestParam String password,
                          HttpSession session) {
        User findUser = userService.findByIdAndPassword(userId, password);
        if (findUser == null) {
            return "redirect:/user/loginFailed";
        }

        session.setAttribute("user", findUser);
        return "redirect:/";
    }

    @RequestMapping("/user/loginV3")
    public String loginV3(String userId,
                          String password,
                          HttpSession session) {
        User findUser = userService.findByIdAndPassword(userId, password);
        if (findUser == null) {
            return "redirect:/user/loginFailed";
        }

        session.setAttribute("user", findUser);
        return "redirect:/";
    }

    @RequestMapping("/user/loginV4")
    public String loginV4(@ModelAttribute User user,
                          HttpSession session) {
        User findUser = userService.findByIdAndPassword(user);
        if (findUser == null) {
            return "redirect:/user/loginFailed";
        }

        session.setAttribute("user", findUser);
        return "redirect:/";
    }

    /**
     * TODO: logout
     */
    @RequestMapping("/user/logout")
    public String logOut(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
