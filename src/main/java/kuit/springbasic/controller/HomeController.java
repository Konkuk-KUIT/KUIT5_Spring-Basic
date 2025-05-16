package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class HomeController {

    private QuestionRepository questionRepository;

    public HomeController(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

//    private final MemoryUserRepository memoryUserRepository1;
//
//
//    public HomeController(MemoryUserRepository memoryUserRepository1) {
//        this.memoryUserRepository1 = memoryUserRepository1;
//    }



    @RequestMapping("/homeV1")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response){
        Collection<Question> questions = questionRepository.findAll();
        return new ModelAndView("home").addObject("questions",questions);
    }

    @RequestMapping("/homeV2")
    public ModelAndView showHomeV2(){
        Collection<Question> questions = questionRepository.findAll();
        return new ModelAndView("home").addObject("questions",questions);
    }

    @RequestMapping("/homeV3")
    public String showHomeV3(Model model){
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return "home";
    }



    @RequestMapping("/")
    public String showHome(Model model){
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return "home";
    }
}
