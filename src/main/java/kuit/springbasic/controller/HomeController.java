package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class HomeController {

    //내부적으로 생성까지 담당하면, 싱글톤으로 관리되지 않아 파생되는 문제가 있음
    private QuestionRepository questionRepository;

    //IOC 제어의 역전 : questionRepository가 언제 쓰이는지, 어디서 온지 모름
    public HomeController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * TODO: showHome
     * showHomeV1 : parameter - HttpServletRequest, HttpServletResponse / return - ModelAndView
     * showHomeV2 : parameter - none / return - ModelAndView
     * showHomeV3 : parameter - Model / return - String
     */
    @RequestMapping("/home")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response){
        Collection<Question> questions = questionRepository.findAll();
        return new ModelAndView("home")
                .addObject("questions",questions);
    }

    @RequestMapping("/homeV2")
    public ModelAndView showHomeV2(){
        Collection<Question> questions = questionRepository.findAll();
        return new ModelAndView("home")
                .addObject("questions",questions);
    }

    @RequestMapping("/homeV3")
    public String showHomeV3(Model model){
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions",questions);
        return "home";
    }

    @RequestMapping("/homeV4")
    public ModelAndView showHomeV4(Model model){
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions",questions);

        return new ModelAndView("home");
    }
}
