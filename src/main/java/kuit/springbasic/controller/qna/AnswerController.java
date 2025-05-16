package kuit.springbasic.controller.qna;

import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.domain.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/api/qna/addAnswer")
    @ResponseBody
    public Map<String, Object> addAnswer(@ModelAttribute Answer answer) {
        Answer saved = answerRepository.insert(answer);

        saved.setWriter(saved.getWriter());

        Map<String, Object> result = new HashMap<>();
        result.put("answer", saved);
        return result;
    }

}
