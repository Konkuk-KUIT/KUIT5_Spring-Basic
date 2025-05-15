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
    // 직접 객체를 생성하지 말고 의존성 주입으로 스프링 컨테이너가 만들어주도록
    // private QuestionRepository questionRepository = new MemoryQuestionRepository();
    private final QuestionRepository questionRepository;

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

    // HttpServletRequest, HttpServletResponse -> 쿠키, 세션 정보 접근 등 필요한 경우에만 명시적으로 사용
    @RequestMapping("/homeV1")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        Collection<Question> questions = questionRepository.findAll();

        // spring.mvc.view.prefix=/WEB-INF/
        // spring.mvc.view.suffix=.jsp
        return new ModelAndView("home")
                .addObject("questions", questions);
    }

    @RequestMapping("/homeV2")
    public ModelAndView showHomeV2() { // HandlerMethodArgumentResolver가 메서드를 실행할 때 요청을 분석하여 적절하게  파라미터를 주입
        Collection<Question> questions = questionRepository.findAll();

        // spring.mvc.view.prefix=/WEB-INF/
        // spring.mvc.view.suffix=.jsp
        return new ModelAndView("home")
                .addObject("questions", questions);
    }

    @RequestMapping("/home")
    public String showHomeV3(Model model) {
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);

        // ViewResolver가 view 경로로 변환
        return "home";
    }

    @RequestMapping("/homeV4")
    public ModelAndView showHomeV4(Model model) {
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);

        // Spring이 Model에 담긴 데이터를 ModelAndView로 자동 복사한다고 하는데 좋은 구조는 아닌듯
        return new ModelAndView("home");
    }
}
