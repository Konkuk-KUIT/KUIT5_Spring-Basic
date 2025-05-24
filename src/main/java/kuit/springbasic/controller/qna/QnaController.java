package kuit.springbasic.controller.qna;

import java.util.List;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import kuit.springbasic.service.AnswerService;
import kuit.springbasic.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class QnaController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * TODO: showQnA
     */
    @RequestMapping("/qna/show")
    public ModelAndView showQnA(@RequestParam("questionId") String questionId) {

        Question question = questionService.findById(Integer.parseInt(questionId));
        List<Answer> answers = answerService.findAllByQuestionId(question.getQuestionId());

        return new ModelAndView("/qna/show")
                .addObject("question", question)
                .addObject("answers", answers);
    }
}
