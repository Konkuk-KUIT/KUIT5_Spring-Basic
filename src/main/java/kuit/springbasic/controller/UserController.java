package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * TODO: showUserForm
     */
    // 회원 가입 폼
    @RequestMapping("/user/form")
    public String showUserForm() {
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    // @RequestMapping("/user/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        userRepository.insert(new User(userId, password, name, email));

        return "redirect:/";
    }

    @RequestMapping("/user/signup")
    public String createUserV2(@ModelAttribute User newUser) {
        userRepository.insert(new User(
                newUser.getUserId(),
                newUser.getPassword(),
                newUser.getName(),
                newUser.getEmail()
        ));

        return "redirect:/";
    }


    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public String showUserList(HttpServletRequest request, Model model) {
        // 세션에서 로그인 유저 정보 가져오기
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 로그인하지 않은 경우 로그인 페이지로
        if (user == null) {
            return "redirect:/user/loginForm";
        }

        // 로그인한 유저 로직
        Collection<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "user/list";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId, Model model) {
        User user = userRepository.findByUserId(userId);
        model.addAttribute("user", user);

        return "user/updateForm";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    // @RequestMapping("/user/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email) {
        User user = userRepository.findByUserId(userId);
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);
        userRepository.update(user);

        return "redirect:/user/list";
    }

    @RequestMapping("/user/update")
    public String updateUserV2(@ModelAttribute User updateUser) {
        userRepository.update(updateUser);

        return "redirect:/user/list";
    }


}
