package kuit.springbasic.controller.qna;

import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QnaController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    /**
     * TODO: showQnA
     */
    @RequestMapping("/qna/show")
    public ModelAndView showQnA(@RequestParam("questionId") String questionId) {
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        List<Answer> answers = answerRepository.findAllByQuestionId(Integer.parseInt(questionId)).stream().toList();

        return new ModelAndView("qna/show")
                .addObject("question", question)
                .addObject("answers", answers);
    }

}
