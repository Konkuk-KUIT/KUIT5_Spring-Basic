package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;
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

    @PostMapping("/user/login")
    public String loginV1(@RequestParam("userId") String userId,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {
        User findUser = userRepository.findByUserId(userId);
        User loginUser = new User(userId, password);

        if (findUser != null && loginUser.isSameUser(findUser)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loginUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @GetMapping("/user/login")
    public String showLogin() {
        return "user/login";
    }

//    @RequestMapping("/user/login")
//    컴파일러 옵션 "-parameters" 플래그 추가 필요
    public String loginV2(@RequestParam String userId,
                          @RequestParam String password,
                          HttpServletRequest request) {
        User findUser = userRepository.findByUserId(userId);
        User loginUser = new User(userId, password);

        if (findUser != null && loginUser.isSameUser(findUser)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loginUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

//    @RequestMapping("/user/login")
    public String loginV3(String userId,
                          String password,
                          HttpServletRequest request) {
        User findUser = userRepository.findByUserId(userId);
        User loginUser = new User(userId, password);

        if (findUser != null && loginUser.isSameUser(findUser)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loginUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

//    @RequestMapping("/user/login")
    public String loginV4(@ModelAttribute User user, HttpServletRequest request) {
        User findUser = userRepository.findByUserId(user.getUserId());
        User loginUser = new User(user.getUserId(), user.getPassword());

        if (findUser != null && loginUser.isSameUser(findUser)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loginUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }


    /**
     * TODO: logout
     */

    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }
}
