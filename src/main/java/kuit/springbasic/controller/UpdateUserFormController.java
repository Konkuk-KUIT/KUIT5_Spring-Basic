package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UpdateUserFormController {

    private final UserRepository userRepository;

    @GetMapping("/users/updateForm")
    public String showUpdateForm(HttpServletRequest request, Model model) {
        String userId = request.getParameter("userId");
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);
        return "user/updateForm";
    }
}
