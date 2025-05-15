package kuit.springbasic.controller.qna;

import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QnaController {
    private final QuestionRepository questionRepository;

    @Autowired
    public QnaController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * TODO: showQnA
     */
    @GetMapping("/qna/show")
    public String showQuestionForm(@RequestParam("questionId") int questionId, Model model) {
        Question question = questionRepository.findByQuestionId(questionId);
        model.addAttribute("question", question);

        return "qna/show";
    }

}
