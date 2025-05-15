package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
    @PostMapping("/api/qna/addAnswerV1")
    public void addAnswerV1(@RequestParam("questionId") int questionId,
                            @RequestParam("writer") String writer,
                            @RequestParam("contents") String contents,
                            HttpServletResponse response) throws IOException {
        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        /**
         * {answerId: 4, questionId: 2, writer: "쿠잇", contents: "1", createdDate: "2025-05-15 02:39:27"}
         */

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("answer", savedAnswer);

        //json 응답 하기
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(responseBody));
    }

    // 모르겠음...
    @ResponseBody
    @PostMapping("/api/qna/addAnswerV2")
    public Map<String, Object> addAnswerV2(@RequestParam("questionId") int questionId,
                                           @RequestParam("writer") String writer,
                                           @RequestParam("contents") String contents) {
        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);
        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        Map<String, Object> model = new HashMap<>();
        model.put("answer", savedAnswer);
        return model;

    }//??

    @ResponseBody
    @PostMapping("/api/qna/addAnswerV3")
    public Answer addAnswerV3(@RequestParam("questionId") int questionId,
                            @RequestParam("writer") String writer,
                            @RequestParam("contents") String contents) {
        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);
        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        return savedAnswer;
    }

    @ResponseBody
    @PostMapping("/api/qna/addAnswerV4")
    public Map<String, Object> addAnswerV3(@ModelAttribute("answer") Answer answer) {
        Answer savedAnswer = answerRepository.insert(answer);
        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        Map<String, Object> result = new HashMap<>();
        result.put("answer", savedAnswer);
        return result;
    }
}
