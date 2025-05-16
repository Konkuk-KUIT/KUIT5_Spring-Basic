package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final QuestionRepository questionRepository;

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

    @RequestMapping("/homeV3")
    public String showHomeV3(Model model) {
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);

        return "home";
    }

    @RequestMapping("/")
    public ModelAndView showHomeV4(Model model) {
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);

        return new ModelAndView("home");
    }
}
