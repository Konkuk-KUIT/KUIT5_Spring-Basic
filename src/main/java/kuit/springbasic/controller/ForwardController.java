package kuit.springbasic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {

    @RequestMapping("/user/form")
    public String forwardUserForm() {
        return "user/form";  // => /WEB-INF/views/user/form.jsp
    }

    @RequestMapping("/user/loginForm")
    public String forwardLoginForm() {
        return "user/login";  // => /WEB-INF/views/user/login.jsp
    }

    @RequestMapping("/user/updateForm")
    public String forwardUpdateForm() {
        return "user/updateForm";  // => /WEB-INF/views/user/updateForm.jsp
    }

    @RequestMapping("/user/loginFailed")
    public String forwardLoginFailed() {
        return "user/loginFailed";  // => /WEB-INF/views/user/loginFailed.jsp
    }
}
