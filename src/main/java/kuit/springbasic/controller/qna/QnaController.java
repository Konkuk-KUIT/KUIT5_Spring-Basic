package kuit.springbasic.controller.qna;

import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/qna")
public class QnaController {

    private final QuestionRepository questionRepository;

    public QnaController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/show")
    public String showQuestion(@RequestParam("questionId") int questionId, Model model) {
        Question question = questionRepository.findByQuestionId(questionId);
        if (question == null) {
            return "redirect:/"; // 없으면 홈으로
        }
        model.addAttribute("question", question);
        return "qna/show";
    }
}
