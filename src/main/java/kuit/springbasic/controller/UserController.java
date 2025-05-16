package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
// @NoArgsConstructor // 파라미터가 없는 기본 생성자 자동 생성
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    /**
     * TODO: showUserForm
     */
    @GetMapping("/form")
    public String showUserForm() {
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @PostMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        userRepository.insert(new User(userId, password, name, email));
        return "redirect:/";
    }

    @PostMapping("/signup/v2")
    public String createUserV2(@ModelAttribute User user) {
        userRepository.insert(user);
        return "redirect:/";
    }


    /**
     * TODO: showUserList
     */
    @GetMapping("/list")
    public String showUserList(HttpServletRequest request, Model model){
        HttpSession httpSession = request.getSession();
        if (UserSessionUtils.isLoggedIn(httpSession)){
            Collection<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUserUpdateForm() {
        return "user/updateForm";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @PostMapping("/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        userRepository.update(new User(userId, password, name, email));
        return "redirect:/user/list";
    }

    @PostMapping("/update/v2")
    public String updateUserV2(@ModelAttribute User user){
        userRepository.update(user);
        return "redirect:/user/list";
    }

}
