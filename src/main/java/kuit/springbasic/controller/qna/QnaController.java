package kuit.springbasic.controller.qna;

import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @GetMapping("/show")
    public String showQuestion(@RequestParam("questionId") int questionId, Model model) {
        Question question = questionRepository.findByQuestionId(questionId);
        if (question == null) {
            return "redirect:/"; // 질문이 없으면 홈으로 이동
        }

        List<Answer> answers = answerRepository.findAllByQuestionId(questionId).stream().toList();

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        model.addAttribute("countOfAnswer", answers.size());
        return "qna/show";
    }

}
