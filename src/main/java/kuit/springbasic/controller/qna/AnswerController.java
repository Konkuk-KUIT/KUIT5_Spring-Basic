package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
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

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */

    //@PostMapping("/api/qna/addAnswer")
    public void addAnswerV0(@RequestParam int questionId,
                            @RequestParam String writer,
                            @RequestParam String contents,
                            HttpServletResponse response) throws IOException {
        Answer answer = new Answer(questionId, writer, contents);
        answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        response.setContentType("application/json");
        response.getWriter().write("{\"result\": \"ok\"}");
    }

    //@PostMapping("/api/qna/addAnswer")
    public String addAnswerV1(@RequestParam int questionId,
                              @RequestParam String writer,
                              @RequestParam String contents,
                              Model model) {
        Answer answer = new Answer(questionId, writer, contents);
        answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        model.addAttribute("answer", answer);
        return "qna/answerResult";
    }

//    @PostMapping("/api/qna/addAnswer")
//    @ResponseBody
    public Map<String, Answer> addAnswerV2(@RequestParam int questionId,
                              @RequestParam String writer,
                              @RequestParam String contents) {
        Answer answer = new Answer(questionId, writer, contents);
        answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        Map<String, Answer> response = new HashMap<>();
        response.put("answer", answer);
        return response;
    }

    @PostMapping("/api/qna/addAnswer")
    @ResponseBody
    public Map<String, Answer> addAnswerV3(@ModelAttribute Answer answer) {
        answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        Map<String, Answer> response = new HashMap<>();
        response.put("answer", answer);
        return response;
    }
}




