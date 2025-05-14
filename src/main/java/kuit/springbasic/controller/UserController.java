package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    /**
     * TODO: showUserForm
     */
    @RequestMapping("/user/form")
    public String showUserForm() {
        return "user/form";
    }


    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @PostMapping("/user/signup")
    public String createUser(@ModelAttribute User user) {
        userRepository.insert(user);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public String showUserList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/user/login";
        }

        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public String updateUserForm(@ModelAttribute User user, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/user/login";
        }

        User loginUser = (User) session.getAttribute("user");

        if (!loginUser.getUserId().equals(user.getUserId())) {
            return "redirect:/user/list";
        }

        model.addAttribute("user", user);
        return "user/updateForm";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

    @RequestMapping("/user/update1")
    public String updateUser1(@RequestParam String userId,
                              @RequestParam String password,
                              @RequestParam String name,
                              @RequestParam String email,
                              HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/user/login";
        }

        User loginUser = (User) session.getAttribute("user");

        if (!loginUser.getUserId().equals(userId)) {
            return "redirect:/user/list";
        }

        User user = new User(userId, password, name, email);
        userRepository.update(user);

        return "redirect:/user/list"; // 수정 완료 후 목록 페이지로 이동
    }


    @RequestMapping("/user/update")
    public String updateUser(@ModelAttribute User user) {
        userRepository.update(user);
        return "redirect:/user/list";
    }

}
