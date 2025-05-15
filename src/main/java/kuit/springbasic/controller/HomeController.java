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
    // Problem : DI & IoC : 컨트롤러는 객체 생성을 하지 않고, 주입을 받아야 한다!
    // private QuestionRepository questionRepository = new MemoryQuestionRepository();

    // Solution : DI, IoC (모두 만족!)
    private QuestionRepository questionRepository;

    @Autowired // 생성자 하나면 지워버려도 됨.
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

    // 파라미터가 없어도 잘 돌아감. WHY???
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
