package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UpdateUserFormController {
    private final UserRepository userRepository;

    @RequestMapping("/user/updateForm")
    public String updateUserForm(@RequestParam("userId")String userId,
                                 HttpServletRequest request,
                                 Model model) {
        HttpSession session = request.getSession();
        User loginedUser = (User)session.getAttribute("user");

        if (loginedUser == null || !loginedUser.getUserId().equals(userId)) {
            return "redirect:/";
        }
        User updateUser = userRepository.findByUserId(userId);
        model.addAttribute("user", updateUser);
        return "user/updateForm";
    }
}
