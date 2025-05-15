package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import kuit.springbasic.domain.User;
import kuit.springbasic.service.UserService;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/user/form")
    public String showUserForm() {
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping(value = "/user/signup")
    public ModelAndView createUserV1(@RequestParam("userId") String userId,
                                     @RequestParam("password") String password,
                                     @RequestParam("name") String name,
                                     @RequestParam("email") String email) {
        User user = new User(userId, password, name, email);
        userService.save(user);
        return new ModelAndView("redirect:/user/list");
    }

    public ModelAndView createUserV2(@ModelAttribute User user) {
        userService.save(user);
        return new ModelAndView("redirect:/user/list");
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public ModelAndView showUserList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            List<User> users = userService.findAll();

            return new ModelAndView("/user/list")
                    .addObject("users", users);
        }
        return new ModelAndView("redirect:/user/loginForm");
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public String execute(@RequestParam("userId") String userId,
                          HttpSession session) throws Exception {
        User user = userService.findById(userId);
        if (userService.isSame(user, (User) session.getAttribute("user"))) {
            return "/user/updateForm";
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping(value = "/user/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) throws Exception {
        User modifiedUser = new User(userId, password, name, email);
        userService.update(modifiedUser);
        return "redirect:/user/list";
    }

    public String updateUserV2(@ModelAttribute User user) throws Exception {
        userService.update(user);
        return "redirect:/user/list";
    }
}
