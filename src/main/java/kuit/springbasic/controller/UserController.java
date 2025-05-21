package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;
import static kuit.springbasic.util.UserSessionUtils.getUserFromSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    /**
     * TODO: showUserForm
     */
    @GetMapping("/form")
    public String showForm() {
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    /*@PostMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        userRepository.insert(new User(userId, password, name, email));
        return "redirect:/user/list";
    }
*/
    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User user) {
        userRepository.insert(user);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @GetMapping("/list")
    public String showUserList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users",userRepository.findAll());
            return "user/list";
        }
        return "redirect:/user/loginForm";
    }


    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUserUpdateForm(HttpServletRequest request,
                                     Model model) {
        String userId = request.getParameter("userId");
        User user = userRepository.findByUserId(userId);

        HttpSession session = request.getSession();

        if (user != null && user.isSameUser(getUserFromSession(session))) {
            model.addAttribute(USER_SESSION_KEY, user);

            return "/user/updateForm";
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    /*@GetMapping("/user/update")
    public String updateUserV1(@RequestParam("userId")String userId,
                               @RequestParam("password")String password,
                               @RequestParam("name")String name,
                               @RequestParam("email")String email) {
        userRepository.update(new User(userId, password, name, email));
        return "redirect:/user/list";
    }*/

    @PostMapping("/update")
    public String updateUserV2(@ModelAttribute User user,
                               HttpServletRequest request) {
        userRepository.update(user);
        HttpSession session = request.getSession();
        session.removeAttribute(USER_SESSION_KEY);
        session.setAttribute(USER_SESSION_KEY, user);
        return "redirect:/user/list";
    }
}
