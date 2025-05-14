package kuit.springbasic.controller.qna;

import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerRepository answerRepository;
    private final MemoryQuestionRepository questionRepository;
    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */
    @PostMapping("/api/qna/addAnswer")
    @ResponseBody
    public Map<String, Object> addAnswer(@ModelAttribute Answer answer) {
        Answer created = answerRepository.insert(answer);
        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.setCountOfAnswer(question.getCountOfAnswer() + 1);
        questionRepository.updateCountOfAnswer(question);

        // 응답 객체 구성
        Map<String, Object> response = new HashMap<>();
        response.put("answer", created);
        return response;
    }

}
