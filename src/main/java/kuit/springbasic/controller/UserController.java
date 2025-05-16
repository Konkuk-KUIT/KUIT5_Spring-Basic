package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
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
        if(userRepository.findByUserId(userId) == null) {
            User user = new User(userId, password, name, email);
            userRepository.insert(user);
        }
        return "redirect:/user/loginForm";
    }

    @RequestMapping("/user/signup")
    public String createUserV2(@ModelAttribute User user) {
        if(userRepository.findByUserId(user.getUserId()) == null) {
            userRepository.insert(user);
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public ModelAndView showUserList(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(UserSessionUtils.isLoggedIn(session)) {
            return new ModelAndView("user/list").addObject("users", userRepository.findAll());
        }
        return new ModelAndView("redirect:/user/loginForm");
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public ModelAndView showUserUpdateForm(@RequestParam("userId") String userId, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(!UserSessionUtils.isLoggedIn(session)) {
            return new ModelAndView("redirect:/user/loginForm");
        }
        User userFromSession = UserSessionUtils.getUserFromSession(session);
        if(!userFromSession.getUserId().equals(userId)){
            return new ModelAndView("redirect:/user/list");
        }
        return new ModelAndView("user/updateForm").addObject("user", userFromSession);
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
        userRepository.update(new User(userId, password, name,email));
        return "redirect:/user/list";
    }

    @RequestMapping("/user/update")
    public String updateUserV2(@ModelAttribute User user) {
        userRepository.update(user);
        return "redirect:/user/list";
    }

}
