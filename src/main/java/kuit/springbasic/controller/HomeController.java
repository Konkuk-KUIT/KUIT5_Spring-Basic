package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class HomeController {

    private QuestionRepository questionRepository;

    @Autowired
    public HomeController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * TODO: showHome
     * showHomeV1 : parameter - HttpServletRequest, HttpServletResponse / return - ModelAndView
     * showHomeV2 : parameter - none / return - ModelAndView
     * showHomeV3 : parameter - Model / return - String
     */

    @RequestMapping("/homeV1")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        Collection<Question> questions = questionRepository.findAll();

        return new ModelAndView("home")
                .addObject("questions", questions);
    }

    @RequestMapping("/homeV2")
    public ModelAndView showHomeV2() {
        Collection<Question> questions = questionRepository.findAll();

        return new ModelAndView("home")
                .addObject("questions", questions);
    }

    @RequestMapping("/")
    public String showHomeV3(Model model) {
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);

        return "home";
    }

    @RequestMapping("/homeV4")
    public ModelAndView showHomeV4(Model model) {
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);

        return new ModelAndView("home");
    }

}
