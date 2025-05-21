package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import kuit.springbasic.domain.Question;
import kuit.springbasic.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final QuestionService questionService;

    /**
     * TODO: showHome
     * showHomeV1 : parameter - HttpServletRequest, HttpServletResponse / return - ModelAndView
     * showHomeV2 : parameter - none / return - ModelAndView
     * showHomeV3 : parameter - Model / return - String
     */
    @RequestMapping
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        List<Question> questions = questionService.findAll();

        return new ModelAndView("home")
                .addObject("questions", questions);
    }

    @RequestMapping("/homeV2")
    public ModelAndView showHomeV2() {
        List<Question> questions = questionService.findAll();

        return new ModelAndView("home")
                .addObject("questions", questions);
    }

    @RequestMapping("/homeV3")
    public String showHomeV3(Model model) {
        List<Question> questions = questionService.findAll();
        model.addAttribute("questions", questions);

        return "home";
    }
}
