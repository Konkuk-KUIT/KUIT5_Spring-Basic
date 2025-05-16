package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.domain.Answer;
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

    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */

    @PostMapping("/qna/addAnswerV1")
    public String addAnswerV1(
            @RequestParam int questionId,
            @RequestParam String writer,
            @RequestParam String contents
    ) {
        Answer answer = new Answer(questionId, writer, contents);
        answerRepository.insert(answer);

        return "redirect:/qna/show?questionId=" + questionId;
    }

    @ResponseBody
    public Map<String, Object> addAnswerV2(
            @RequestParam int questionId,
            @RequestParam String writer,
            @RequestParam String contents
    ) {
        Answer answer = new Answer(questionId, writer, contents);
        answerRepository.insert(answer);

        Map<String, Object> answerData = new HashMap<>();
        answerData.put("writer", answer.getWriter());
        answerData.put("createdDate", answer.getCreatedDate());
        answerData.put("contents", answer.getContents());
        answerData.put("answerId", answer.getAnswerId());

        Map<String, Object> result = new HashMap<>();
        result.put("answer", answerData);
        return result;
    }

    @PostMapping("/api/qna/addAnswer")
    @ResponseBody
    public Map<String, Object> addAnswerV3(
                            @RequestParam int questionId,
                            @RequestParam String writer,
                            @RequestParam String contents
                            )
    {
        System.out.println("add Answer");

        Answer answer = new Answer(questionId, writer, contents);
        answerRepository.insert(answer);

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> answerData = new HashMap<>();

        answerData.put("writer", answer.getWriter());
        answerData.put("createdDate", answer.getCreatedDate()); // 형식이 Date면 JS에서 toString 처리됨
        answerData.put("contents", answer.getContents());
        answerData.put("answerId", answer.getAnswerId());

        result.put("answer", answerData);

        return result;
    }

    public Map<String, Object> addAnswerV4(
            @ModelAttribute Answer answer
    )
    {
        answerRepository.insert(answer);

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> answerData = new HashMap<>();

        answerData.put("writer", answer.getWriter());
        answerData.put("createdDate", answer.getCreatedDate()); // 형식이 Date면 JS에서 toString 처리됨
        answerData.put("contents", answer.getContents());
        answerData.put("answerId", answer.getAnswerId());

        result.put("answer", answerData);

        return result;
    }

}
