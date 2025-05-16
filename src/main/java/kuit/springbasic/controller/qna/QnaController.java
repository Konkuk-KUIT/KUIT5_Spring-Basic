package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
public class QnaController {

    private final QuestionRepository questionRepository;


    @RequestMapping("/qna/show")
    public String showQnA(@RequestParam("questionId")Integer questionId, Model model)
    {
        Question question = questionRepository.findByQuestionId(questionId);
        model.addAttribute("question",question);
        return "qna/show";
    }


}
