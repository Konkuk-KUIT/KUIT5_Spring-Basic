package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.UserRepository;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    /**
     * TODO: showUserForm
     */
    @GetMapping("/user/form")
    public String showSignupForm() {
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @PostMapping("/user/signupV1")
    public String createUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        User user = new User(userId, password, name, email);
        userRepository.insert(user);

        return "redirect:/user/list";
    }

    @PostMapping("/user/signupV2")
    public String createUserV2(@ModelAttribute("user") User user) {
        userRepository.insert(user);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @GetMapping("/user/list")
    public String showUserList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", userRepository.findAll());
            return "user/list";
        }
        return "redirect:/user/login";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/user/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId,
                                     HttpServletRequest request) {
        User user = userRepository.findByUserId(userId);

        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");

        if (user != null && value != null) {
            if (user.equals(value)) {
                return "user/updateForm";
            }
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @PostMapping("/user/updateV1")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        User modifiedUser = new User(userId, password, name, email);
        userRepository.update(modifiedUser);
        return "redirect:/user/list";
    }

    @PostMapping("/user/updateV2")
    public String updateUserV2(@ModelAttribute("user") User user) {
        userRepository.update(user);
        return "redirect:/user/list";
    }

}
