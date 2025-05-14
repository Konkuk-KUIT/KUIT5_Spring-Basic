package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    /**
     * TODO: showUserForm
     */

    @RequestMapping("/user/form")
    public String showLoginForm(){
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */

    @RequestMapping("/user/signup")
    public String createUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email){

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

    @RequestMapping("/user/list")
    public String showUserList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    /**
     * TODO: showUserUpdateForm
     */


    @RequestMapping("/user/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId,
                                     HttpServletRequest request){

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if(user == null){
            return "redirect:/";
        }

        if(user.getUserId().equals(userId)){
            return "user/updateForm";
        }

        return "redirect:/";

    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

    @RequestMapping("/user/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email){

        User user = new User(userId, password, name, email);
        userRepository.update(user);

        return "redirect:/";
    }

    public String updateUserV2(@ModelAttribute User user){
        userRepository.update(user);
        return "redirect:/";
    }

}
