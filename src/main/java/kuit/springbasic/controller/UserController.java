package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    public String showUserForm(){
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/signup")
    public String createUser(@RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email){
        userRepository.insert(new User(userId, password, name, email));
        return "redirect:/";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    public String showUserList(HttpServletRequest request,
                               Model model){
        HttpSession session = request.getSession();
        if(UserSessionUtils.isLoggedIn(session)){
            Collection<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public String showUserUpdateForm(){
        return "/user/updateForm";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/update")
    public String updateUser(@RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email){
        User modifiedUser = new User(userId, password, name, email);
        userRepository.update(modifiedUser);
        return "redirect:/user/list";
    }

}
