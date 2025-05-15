package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
//    @RequestMapping("/user/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                                     @RequestParam("password") String password,
                                     @RequestParam("name") String name,
                                     @RequestParam("email") String email) {
        if (userRepository.findByUserId(userId) == null) {
            User user = new User(userId, password, name, email);
            userRepository.insert(user);
        }
        return "redirect:/";
    }

    @RequestMapping("/user/signup")
    public String createUserV2(@ModelAttribute User user) {
        if (userRepository.findByUserId(user.getUserId()) == null) {
            userRepository.insert(user);
            return "redirect:/";
        }
        return "redirect:/user/login";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public ModelAndView showUserList() {
        return new ModelAndView("user/list")
                .addObject("users", userRepository.findAll());
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public ModelAndView showUserUpdateForm(@RequestParam("userId") String userId,
                                           HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object object = session.getAttribute("user");
        if (object == null)
            return new ModelAndView("redirect:/user/login");

        User user = (User) object;
        if (!userId.equals(user.getUserId()))
            return new ModelAndView("redirect:/user/login");

        return new ModelAndView("user/updateForm")
                .addObject("user", userRepository.findByUserId(userId));
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
//    @RequestMapping("/user/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        userRepository.update(new User(userId, password, name, email));
        return "redirect:/user/list";
    }

    @RequestMapping("/user/update")
    public String updateUserV2(@ModelAttribute User user) {
        userRepository.update(user);
        return "redirect:/user/list";
    }
}
