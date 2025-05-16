package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public String createUser(@RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email) {
        User user = new User(userId, password, name, email);
        userRepository.insert(user);
        return "redirect:/homeV2";
    }

    @GetMapping
    public String showUserList(Model model) {
        Collection<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/updateForm")
    public String showUserUpdateForm(HttpServletRequest request, Model model) {
        String userId = request.getParameter("userId");
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email) {
        User user = new User(userId, password, name, email);
        userRepository.update(user);
        return "redirect:/users";
    }
}
