package kuit.springbasic.controller;

import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CreateUserController {
    private final UserRepository userRepository;

    @RequestMapping("/user/form")
    public String showUserForm(){
        return "user/form";
    }
    @PostMapping("/user/signup")
    public String createUser(@RequestParam("userId")String userId,
                           @RequestParam("password")String password,
                           @RequestParam("name")String name,
                           @RequestParam("email")String email
                           ){
        User user = new User(userId, password, name, email);
        userRepository.insert(user);
        return "redirect:/user/loginForm";
    }
}
