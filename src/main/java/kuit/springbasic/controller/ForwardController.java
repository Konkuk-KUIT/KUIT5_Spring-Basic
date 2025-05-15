package kuit.springbasic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {
    @RequestMapping("/")
    public String fowardToHome(){
        return "forward:/home";
    }
}
