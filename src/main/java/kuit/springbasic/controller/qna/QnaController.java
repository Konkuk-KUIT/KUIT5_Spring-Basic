package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class QnaController {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    /**
     * TODO: showQnA
     */
    @GetMapping("/qna/show")
    public String showQna(@RequestParam("questionId") int questionId,
                          Model model,
                          HttpServletRequest request) {
        Question question = questionRepository.findByQuestionId(questionId);
        Collection<Answer> answerByQuestionId = answerRepository.findAllByQuestionId(questionId);

        model.addAttribute("question", question);
        model.addAttribute("answers", answerByQuestionId);
        return "qna/show";

    }
}
