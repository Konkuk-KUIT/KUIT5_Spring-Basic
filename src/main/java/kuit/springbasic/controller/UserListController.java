package kuit.springbasic.controller;

import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@RequiredArgsConstructor
@Controller
public class UserListController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public String listUsers(Model model) {
        Collection<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }
}
