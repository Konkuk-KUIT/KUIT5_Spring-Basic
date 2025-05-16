package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */

    @RequestMapping("/api/qna/addAnswer")
    public void addAnswerV0(
            @RequestParam("questionId") String questionId,
            @RequestParam("writer") String writer,
            @RequestParam("contents") String contents,
            HttpServletResponse response
            ) throws IOException {
        Answer answer = new Answer(Integer.parseInt(questionId), writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(savedAnswer.getQuestionId());
        question.increaseCountOfAnswer();
        questionRepository.update(question);

        Map<String, Object> model = new HashMap<>();
        model.put("answer", savedAnswer);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(model);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    }

}
