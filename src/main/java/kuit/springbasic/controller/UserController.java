package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
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
    private UserRepository userRepository;

    //IOC 제어의 역전 : questionRepository가 언제 쓰이는지, 어디서 온지 모름
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository= userRepository;
    }

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
        userRepository.insert(newUser);
        return "redirect:/";
    }
    @RequestMapping("/user/signup2")
    public String createUserV2(@ModelAttribute User newUser) {
        userRepository.insert(newUser);
        return "redirect:/";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public ModelAndView showUserList(){
        Collection<User> users = userRepository.findAll();
        return new ModelAndView("user/list")
                .addObject("users", users);
    }
    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public ModelAndView showUserUpdateForm(@RequestParam("userId")String userId){
        User target = userRepository.findByUserId(userId);
        return new ModelAndView("user/updateForm")
                .addObject("user", target);
    }
    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/user/updateV1")
    public String updateUserV1(@RequestParam("userId")String userId, @RequestParam("password")String password, @RequestParam("name")String name, @RequestParam("email")String email){
        User target = userRepository.findByUserId(userId);
        target.update(new User(userId,password,name,email));
        return "redirect:/user/list";
    }
    @RequestMapping("/user/update")
    public String updateUserV2(@ModelAttribute User user){
        User target = userRepository.findByUserId(user.getUserId());
        target.update(user);
        return "redirect:/user/list";
    }

}
