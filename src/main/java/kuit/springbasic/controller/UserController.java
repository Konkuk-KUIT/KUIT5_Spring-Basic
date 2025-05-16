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

import java.util.Collection;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;



    @RequestMapping("/form")
    public String showUserForm(){
        return "/user/form";
    }

    @RequestMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email){

        User created = new User(userId, password, name, email);
        userRepository.insert(created);

        return "redirect:/";
    }


//    @RequestMapping("/signup")
    public String createUserV2(@ModelAttribute User user){

        userRepository.insert(user);

        return "redirect:/";
    }




    @RequestMapping("/list")
    public String showUserList(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session!=null && session.getAttribute("user")!=null) {
            Collection<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "/user/list";
        }
        return "redirect:/user/login";
    }



    @RequestMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId,
                                     HttpServletRequest request){

        HttpSession session = request.getSession(false);

        User user = null;

        if(session!=null) {
            user = (User) session.getAttribute("user");
        }

        if(user!=null && user.getUserId().equals(userId)){
            return "user/updateForm";
        }

        return "redirect:/user/list";
    }

//    @RequestMapping("/update")
    public String updateUserV1(final @RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email)
    {
        User updateUser = new User(userId, password, name, email);
        userRepository.update(updateUser);

        return "redirect:/user/list";
    }



    @RequestMapping("/update")
    public String updateUserV1(@ModelAttribute User updateUser)
    {
        userRepository.update(updateUser);

        return "redirect:/user/list";
    }



}
