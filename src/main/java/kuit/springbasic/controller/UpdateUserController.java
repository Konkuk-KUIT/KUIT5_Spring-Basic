package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UpdateUserController {

    private final UserRepository userRepository;

    @Autowired
    public UpdateUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users/update")
    public ModelAndView updateUser(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        User user = new User(userId, password, name, email);
        userRepository.update(user);

        return new ModelAndView("redirect:/users");
    }
}
