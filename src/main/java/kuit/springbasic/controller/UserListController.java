package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class UserListController {
    private final UserRepository userRepository;

    @RequestMapping("/user/list")
    public String list(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null) {
            return "redirect:/user/loginForm";
        }
        Collection<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "user/list";
    }
}
