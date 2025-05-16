package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @PostMapping("/api/qna/addAnswer")
    public void addAnswer(@ModelAttribute Answer answer, HttpServletResponse response) throws IOException {
        answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        questionRepository.update(question);

        Map<String, Object> model = new HashMap<>();
        model.put("answer", answer);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(model));
    }
}
