package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AnswerController {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerController(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */
    // HttpServletResponse를 직접 조작해 JSON을 추가하는 방식
    //@PostMapping("/api/qna/addAnswer")
    public void addAnswerV0(@RequestParam("questionId") int questionId,
                              @RequestParam("writer") String writer,
                              @RequestParam("contents") String contents,
                              HttpServletResponse response) throws IOException {
        Answer answer = new Answer(questionId, writer, contents);
        answerRepository.insert(answer);

        Question answerQuestion = questionRepository.findByQuestionId(questionId);
        answerQuestion.increaseCountOfAnswer();
        questionRepository.update(answerQuestion);

        /*
        {
          "result": "success",
          "questionId": 1,
          "answerCount": 3,
          "answer": {
            "answerId": 12,
            "writer": "쿠잇",
            "contents": "ㅋㅋ",
            "createdDate": "2025-05-16 05:00:00"
          }
        }
        * */
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"result\":\"success\",")
                .append("\"questionId\":").append(questionId).append(",")
                .append("\"answerCount\":").append(answerQuestion.getCountOfAnswer()).append(",")
                .append("\"answer\":{")
                .append("\"answerId\":").append(answer.getAnswerId()).append(",")
                .append("\"writer\":\"").append(answer.getWriter()).append("\",")
                .append("\"createdDate\":\"").append(answer.getCreatedDate()).append("\",")
                .append("\"contents\":\"").append(answer.getContents()).append("\"")
                .append("}")
                .append("}");

        response.getWriter().write(sb.toString());
    }

    // WebConfig에 ViewResolver 등록해놓고, Model에 Map 형태로 데이터를 담아 jsonView라는 View 이름 반환
    //@PostMapping("/api/qna/addAnswer")
    public String addAnswerV1(@RequestParam("questionId") int questionId,
                              @RequestParam("writer") String writer,
                              @RequestParam("contents") String contents,
                              Model model) {

        Answer savedAnswer = answerRepository.insert(new Answer(questionId, writer, contents));
        Question answerQuestion = questionRepository.findByQuestionId(questionId);
        answerQuestion.increaseCountOfAnswer();
        questionRepository.update(answerQuestion);

        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("questionId", questionId);
        result.put("answerCount", answerQuestion.getCountOfAnswer());

        Map<String, Object> answerDetail = new HashMap<>();
        answerDetail.put("answerId", savedAnswer.getAnswerId());
        answerDetail.put("writer", savedAnswer.getWriter());
        answerDetail.put("createdDate", savedAnswer.getCreatedDate().toString());
        answerDetail.put("contents", savedAnswer.getContents());

        result.put("answer", answerDetail);
        model.addAttribute("json", result);

        return "jsonView"; // WebConfig에서 등록한 View 이름
    }

    // @ResponseBody를 사용해서 반환하는 Map이 자동으로 JSON 변환되도록 처리
    //@PostMapping("/api/qna/addAnswer")
    @ResponseBody
    public Map<String, Object> addAnswerV2(@RequestParam("questionId") int questionId,
                                           @RequestParam("writer") String writer,
                                           @RequestParam("contents") String contents) {
        Answer savedAnswer = answerRepository.insert(new Answer(questionId, writer, contents));

        Question answerQuestion = questionRepository.findByQuestionId(questionId);
        answerQuestion.increaseCountOfAnswer();
        questionRepository.update(answerQuestion);

        Map<String, Object> answerDetail = new HashMap<>();
        answerDetail.put("answerId", savedAnswer.getAnswerId());
        answerDetail.put("writer", savedAnswer.getWriter());
        answerDetail.put("createdDate", savedAnswer.getCreatedDate().toString());
        answerDetail.put("contents", savedAnswer.getContents());

        return Map.of(
                "result", "success",
                "questionId", questionId,
                "answerCount", answerQuestion.getCountOfAnswer(),
                "answer", answerDetail
        );
    }

    // @ModelAttribute를 사용한 버젼
    @PostMapping("/api/qna/addAnswer")
    @ResponseBody
    public Map<String, Object> addAnswerV3(@ModelAttribute Answer answer) {
        Answer savedAnswer = answerRepository.insert(answer);

        Question answerQuestion = questionRepository.findByQuestionId(answer.getQuestionId());
        answerQuestion.increaseCountOfAnswer();
        questionRepository.update(answerQuestion);

        Map<String, Object> answerDetail = new HashMap<>();
        answerDetail.put("answerId", savedAnswer.getAnswerId());
        answerDetail.put("writer", savedAnswer.getWriter());
        answerDetail.put("createdDate", savedAnswer.getCreatedDate().toString());
        answerDetail.put("contents", savedAnswer.getContents());

        return Map.of(
                "result", "success",
                "questionId", answer.getQuestionId(),
                "answerCount", answerQuestion.getCountOfAnswer(),
                "answer", answerDetail
        );
    }
}
