package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.domain.User;
import kuit.springbasic.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreateUserController {

    private final UserRepository userRepository;

    @Autowired
    public CreateUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users/create")
    public ModelAndView createUser(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        User user = new User(userId, password, name, email);
        userRepository.insert(user);

        return new ModelAndView("redirect:/homeV2");
    }
}
