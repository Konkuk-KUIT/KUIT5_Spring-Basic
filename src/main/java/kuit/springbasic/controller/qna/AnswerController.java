package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import kuit.springbasic.service.AnswerService;
import kuit.springbasic.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */
    @PostMapping("/api/qna/addAnswer")
    public void addAnswerV0(@RequestParam("questionId") String questionId,
                            @RequestParam("writer") String writer,
                            @RequestParam("contents") String contents,
                            HttpServletResponse response) throws IOException {
        Answer answer = new Answer(Integer.parseInt(questionId), writer, contents);

        Answer savedAnswer = answerService.save(answer);
        Question question = questionService.findByQuestionId(answer.getQuestionId());

        questionService.updateCountOfAnswer(question);
        Map<String, Object> model = new HashMap<>();
        model.put("answer", savedAnswer);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(model);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    public String addAnswerV1(@RequestParam("questionId") String questionId,
                              @RequestParam("writer") String writer,
                              @RequestParam("contents") String contents,
                              Model model) {
        Answer answer = new Answer(Integer.parseInt(questionId), writer, contents);

        Answer savedAnswer = answerService.save(answer);
        Question question = questionService.findByQuestionId(answer.getQuestionId());

        questionService.updateCountOfAnswer(question);
        model.addAttribute("answer", savedAnswer);
        return "jsonView";
    }

    @ResponseBody
    public Map<String, Object> addAnswerV2(@RequestParam("questionId") String questionId,
                                           @RequestParam("writer") String writer,
                                           @RequestParam("contents") String contents) {
        Answer answer = new Answer(Integer.parseInt(questionId), writer, contents);

        Answer savedAnswer = answerService.save(answer);
        Question question = questionService.findByQuestionId(answer.getQuestionId());

        questionService.updateCountOfAnswer(question);
        Map<String, Object> model = new HashMap<>();
        model.put("answer", answer);
        return model;
    }

    @ResponseBody
    public Map<String, Object> addAnswerV2(@ModelAttribute Answer answer) {
        Answer savedAnswer = answerService.save(answer);
        Question question = questionService.findByQuestionId(answer.getQuestionId());

        questionService.updateCountOfAnswer(question);
        Map<String, Object> model = new HashMap<>();
        model.put("answer", answer);
        return model;
    }
}
