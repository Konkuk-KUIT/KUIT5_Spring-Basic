package kuit.springbasic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ForwardController {


    @RequestMapping("/users/form")
    public ModelAndView forwardToUserForm() {
        return new ModelAndView("user/form");
    }

    @RequestMapping("/users/loginForm")
    public ModelAndView forwardToLoginForm() {
        return new ModelAndView("user/login");
    }


    @RequestMapping("/users/updateForm")
    public ModelAndView forwardToUpdateForm() {
        return new ModelAndView("user/updateForm");
    }
}
