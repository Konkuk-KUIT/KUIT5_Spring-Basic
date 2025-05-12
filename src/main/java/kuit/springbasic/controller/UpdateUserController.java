package kuit.springbasic.controller;

import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UpdateUserController {
    private final UserRepository userRepository;

    @RequestMapping("/user/update")
    public String updateUser(@RequestParam("userId")String userId,
                             @RequestParam("password")String password,
                             @RequestParam("name")String name,
                             @RequestParam("email")String email) {
        User updateUser = new User(userId, password, name, email);
        userRepository.update(updateUser);

        return "redirect:/user/list";
    }
}
