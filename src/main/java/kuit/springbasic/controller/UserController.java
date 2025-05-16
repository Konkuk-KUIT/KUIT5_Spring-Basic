package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/signup")
    public String createUserV1(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ){
        User user = new User(userId, password, name, email);
        userRepository.insert(user);
        return "redirect:/";
    }

    public String createUserV2(@ModelAttribute User user){
        userRepository.insert(user);
        return "redirect:/";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    public ModelAndView showUserList(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)){
            List<User> users = userRepository.findAll().stream().toList();

            return new ModelAndView("user/list")
                    .addObject("users", users);
        }
        return new ModelAndView("redirect:/user/loginForm");
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public String showUserUpdateForm(
            @RequestParam("userId") String userId,
            HttpSession session
    ){
        User user = userRepository.findByUserId(userId);
        User sessionUser = (User) session.getAttribute("user");
        if (user.isSameUser(sessionUser)){
            return "user/updateForm";
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/update")
    public String updateUserV1(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ) throws Exception {
        User updateUser = new User(userId, password, name, email);
        userRepository.update(updateUser);
        return "redirect:/user/list";
    }

    public String updateUserV2(@ModelAttribute User user) throws Exception {
        userRepository.update(user);
        return "redirect:/user/list";
    }

}
