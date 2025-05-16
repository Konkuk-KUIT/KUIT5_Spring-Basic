package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/form")
    public String showUserForm() {
        return "user/form";
    }

    @PostMapping("/create/v1")
    public ModelAndView createUserV1(@RequestParam String userId,
                                     @RequestParam String password,
                                     @RequestParam String name,
                                     @RequestParam String email) {
        User user = new User(userId, password, name, email);
        userRepository.insert(user);
        return new ModelAndView("redirect:/users");
    }

    @PostMapping("/create/v2")
    public String createUserV2(@ModelAttribute User user) {
        userRepository.insert(user);
        return "redirect:/users";
    }

    @GetMapping
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/updateForm/v1")
    public String showUserUpdateFormV1(HttpServletRequest request, Model model) {
        String userId = request.getParameter("userId");
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @GetMapping("/updateForm/v2")
    public String showUserUpdateFormV2(@RequestParam String userId, Model model) {
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/update/v2")
    public String updateUserV2(@ModelAttribute User user) {
        userRepository.update(user);
        return "redirect:/users";
    }
}
