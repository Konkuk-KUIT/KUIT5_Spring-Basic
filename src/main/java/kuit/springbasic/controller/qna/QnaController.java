package kuit.springbasic.controller.qna;

import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class QnaController {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QnaController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    /**
     * TODO: showQnA
     */
    @GetMapping("/qna/show")
    public String showQuestionForm(@RequestParam("questionId") int questionId, Model model) {
        Question question = questionRepository.findByQuestionId(questionId);
        model.addAttribute("question", question);

        Collection<Answer> answers = answerRepository.findAllByQuestionId(questionId);
        model.addAttribute("answers", answers);

        return "qna/show";
    }

}
