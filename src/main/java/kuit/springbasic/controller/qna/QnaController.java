package kuit.springbasic.controller.qna;

import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class QnaController {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    /**
     * TODO: showQnA
     */
    // 쿼리 파라미터는 경로에 포함하면 안됨.
    // @RequestParam로 받아야함
    @RequestMapping("qna/show")
    public String showQnA(@RequestParam("questionId")int questionId,
                          Model model){
        Question question = questionRepository.findByQuestionId(questionId);
        Collection<Answer> answers = answerRepository.findAllByQuestionId(questionId);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "qna/show";
    }
}
