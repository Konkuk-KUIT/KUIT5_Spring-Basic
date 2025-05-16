package kuit.springbasic.controller.qna;

import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class QnaController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    /**
     * TODO: showQnA
     */

    @GetMapping("/qna/show")
    public String showQuestion(@RequestParam("questionId") int questionId, Model model) {
        Question question = questionRepository.findByQuestionId(questionId);
        Collection<Answer> answers = answerRepository.findAllByQuestionId(questionId);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "qna/show";  // → /WEB-INF/views/qna/show.jsp로 매핑됨 (기본 설정 기준)
    }


}
