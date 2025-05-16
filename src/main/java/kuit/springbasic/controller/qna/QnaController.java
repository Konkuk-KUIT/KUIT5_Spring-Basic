package kuit.springbasic.controller.qna;

import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class QnaController {

    private final QuestionRepository questionRepository;

    /**
     * TODO: showQnA
     */

    @RequestMapping("/qna/show")
    public String showQnA(@RequestParam("questionId") int questionId, Model model) {

        Question question = questionRepository.findByQuestionId(questionId);
        model.addAttribute("question", question);
        return "qna/show"; // 보여줄 뷰 페이지
    }

}
