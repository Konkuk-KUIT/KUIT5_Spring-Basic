package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/user/form")
    public String showUserForm(){
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
//    @RequestMapping("/user/signup")
//    public String createUser(@RequestParam("userId")String userId,
//                             @RequestParam("password")String password,
//                             @RequestParam("name")String name,
//                             @RequestParam("email")String email){
//        User user = new User(userId,password,name,email);
//        userRepository.insert(user);
//        return "redirect:/user/loginForm";
//    }
    @RequestMapping("/user/signup")
    public String createUser(@ModelAttribute User user){
        userRepository.insert(user);
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public String showUserList(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            return "redirect:/user/loginForm";
        }
        Collection<User> users = userRepository.findAll();
        model.addAttribute("users",users);

        return "user/list";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public String showUserUpdateForm(@RequestParam("userId")String userId,
                                     HttpServletRequest httpServletRequest,
                                     Model model){
        HttpSession session = httpServletRequest.getSession();
        User loginUser = (User)session.getAttribute("user");
        if(!loginUser.getUserId().equals(userId)){
            return "redirect:/";
        }
        User updateUser = userRepository.findByUserId(loginUser.getUserId());
        model.addAttribute(updateUser);
        return "user/updateForm";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
//    @RequestMapping("/user/update")
//    public String updateUser(@RequestParam("userId")String userId,
//                             @RequestParam("password")String password,
//                             @RequestParam("name")String name,
//                             @RequestParam("email")String email){
//        User updateUser = new User(userId,password,name,email);
//        userRepository.update(updateUser);
//
//        return "redirect:/user/list";
//    }
    @RequestMapping("/user/update")
    public String updateUser(@ModelAttribute User updateUser){
        userRepository.update(updateUser);
        return "redirect:/user/list";
    }
}
