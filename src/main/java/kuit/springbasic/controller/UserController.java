package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class UserController {
    private UserService userService;

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

    @RequestMapping("/user/signup")
    public String createUserV1(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email){
        User newUser = new User(userId,password,name,email);
        userService.save(newUser);
        return "redirect:/";
    }
    @RequestMapping("/user/signup2")
    public String createUserV2(@ModelAttribute User newUser) {
        userService.save(newUser);
        return "redirect:/";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public ModelAndView showUserList(){
        Collection<User> users = userService.findAll();
        return new ModelAndView("user/list")
                .addObject("users", users);
    }
    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public ModelAndView showUserUpdateForm(@RequestParam("userId")String userId, HttpSession session){
        User target = userService.findById(userId);
        if(userService.isSame(target, (User)session.getAttribute("user"))){
            return new ModelAndView("user/updateForm")
                    .addObject("user", target);
        }
        return new ModelAndView("redirect:/");
    }
    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/user/updateV1")
    public String updateUserV1(@RequestParam("userId")String userId, @RequestParam("password")String password, @RequestParam("name")String name, @RequestParam("email")String email){
        User modifiedUser = new User(userId,password,name,email);
        userService.update(modifiedUser);
        return "redirect:/user/list";
    }
    @RequestMapping("/user/update")
    public String updateUserV2(@ModelAttribute User user){
        userService.update(user);
        return "redirect:/user/list";
    }

}
