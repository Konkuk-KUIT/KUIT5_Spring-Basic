package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;



    /**
     * TODO: showLoginForm
     */

    @RequestMapping("/user/loginForm")
    public String showLoginForm(){
        return "user/login";
    }

    @GetMapping("/user/login")
    public String redirectToLoginForm() {
        return "redirect:/user/loginForm";
    }
    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/user/loginFailed")
    public String showLoginFailed(){
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
    public String login(@RequestParam("userId")String userId,
            @RequestParam("password")String password,
            HttpServletRequest request){
            User findUser =userRepository.findByUserId(userId);
            User loginUser = new User(userId,password);
            if(findUser != null&& loginUser.isSameUser(findUser)){
            HttpSession session =request.getSession();
            session.setAttribute("user",loginUser);
            return "redirect:/";
            }
            return "redirect:/user/loginFailed";
    }

    @RequestMapping("/user/loginV2")
    public String loginV2(@RequestParam String userId,
                          @RequestParam String password,
                          HttpServletRequest request) {

        User findUser = userRepository.findByUserId(userId);

        if (findUser != null && findUser.isSameUser(userId,password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", findUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @RequestMapping("/user/loginV3")
    public String loginV3(String userId, String password, HttpServletRequest request) {

        User findUser = userRepository.findByUserId(userId);
        if (findUser != null && findUser.isSameUser(userId,password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", findUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @RequestMapping("/user/loginV4")
    public String loginV4(@ModelAttribute User loginUser, HttpServletRequest request) {

        User findUser = userRepository.findByUserId(loginUser.getUserId());
        if (findUser != null && loginUser.isSameUser(findUser)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", findUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 기존 세션이 있으면 가져오고, 없으면 null
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/";
    }

}
