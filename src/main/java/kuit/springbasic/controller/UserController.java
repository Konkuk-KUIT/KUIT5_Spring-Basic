package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    /**
     * TODO: showUserForm - 회원가입 화면 출력
     */
    @GetMapping("/user/form")
    public String showUserForm() {
        return "user/form";
    }

    /**
     * TODO: createUserV1 - @RequestParam 방식으로 회원가입 처리
     */
    @PostMapping("/user/signupV1")
    public String createUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {

        User user = new User(userId, password, name, email);
        userRepository.insert(user);
        return "redirect:/";
    }

    /**
     * TODO: createUserV2 - @ModelAttribute 방식으로 회원가입 처리
     */
    @PostMapping("/user/signup")
    public String createUserV2(@ModelAttribute User user) {
        userRepository.insert(user);
        return "redirect:/";
    }

    /**
     * TODO: showUserList - 전체 사용자 목록 출력
     */
    @GetMapping("/user/list")
    public String showUserList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        User loginUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (loginUser == null) {
            return "redirect:/user/loginForm";  // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
        }

        Collection<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    /**
     * TODO: showUserUpdateForm - 개인정보 수정 화면 출력
     */
    @GetMapping("/user/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId, Model model) {
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    /**
     * TODO: updateUserV1 - @RequestParam 방식으로 개인정보 수정 처리
     */
    @PostMapping("/user/updateV1")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               HttpServletRequest request) {

        User user = new User(userId, password, name, email);
        userRepository.update(user);

        // 세션에 사용자 정보 갱신
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("user", user);
        }

        return "redirect:/";
    }

    /**
     * TODO: updateUserV2 - @ModelAttribute 방식으로 개인정보 수정 처리
     */
    @PostMapping("/user/update")
    public String updateUserV2(@ModelAttribute User user, HttpServletRequest request) {
        userRepository.update(user);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("user", user);
        }

        return "redirect:/";
    }
}