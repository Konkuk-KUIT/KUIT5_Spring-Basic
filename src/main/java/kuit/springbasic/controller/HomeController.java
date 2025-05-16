package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Collections;

@Controller
@RequiredArgsConstructor // final 키워드 붙은 필드에 대한 생성자 추가
public class HomeController {
    private final QuestionRepository questionRepository;


    //     * TODO: showHome
//     * showHomeV1 : parameter - HttpServletRequest, HttpServletResponse / return - ModelAndView
    @GetMapping("/home/v1")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        Collection<Question> questions = questionRepository.findAll();
        return new ModelAndView("home").addObject("questions", questions);
    }

    //     * showHomeV2 : parameter - none / return - ModelAndView
    @GetMapping("/home/v2")
    public ModelAndView showHomeV2() {
        return new ModelAndView("home").addObject("questions", questionRepository.findAll());
    }

    //     * showHomeV3 : parameter - Model / return - String
    @GetMapping({"/", "/home"})
    public String showHomeV3(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "home";
    }
}
