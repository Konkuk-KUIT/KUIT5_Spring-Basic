package kuit.springbasic.controller;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    /**
     * TODO: showUserForm
     */

    @GetMapping("/user/form")
    public String showUserForm() {
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */


    //@PostMapping("/user/signup")
    public String createUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email) {
        User user = new User(userId, password, name, email);
        userRepository.insert(user);

        return "redirect:/user/list";
    }

    @RequestMapping("/user/signup")
    public String createUserV2(@ModelAttribute User user) {
        userRepository.insert(user);
        return "redirect:/user/list";
    }



    /**
     * TODO: showUserList
     */

    @GetMapping("/user/list")
    public String showUserList(HttpSession session, Model model) {
        if (UserSessionUtils.isLogined(session)) {
            model.addAttribute("users", userRepository.findAll());
            return "user/list";
        }

        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */

    @RequestMapping("/user/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId, HttpSession session, Model model) {
        User user = userRepository.findByUserId(userId);

        Object value = session.getAttribute("user");

        if (user != null && value != null) {
            if (user.getUserId().equals(((User)value).getUserId())) {
                model.addAttribute("user",user);
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

    //@PostMapping("/user/update")
    public String updateUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email) {
        User modifiedUser = new User(userId, password, name, email);
        userRepository.update(modifiedUser);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/update")
    public String updateUserV2(@ModelAttribute User user) {
        userRepository.update(user);
        return "redirect:/user/list";
    }

}
