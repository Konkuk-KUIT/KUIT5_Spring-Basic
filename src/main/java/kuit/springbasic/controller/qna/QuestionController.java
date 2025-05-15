package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionRepository questionRepository;

    /**
     * TODO: showQuestionForm
     */
    @GetMapping("/qna/form")
    public String showQuestionForm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            return "qna/form";
        }
        return "redirect:/user/login";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @PostMapping("/qna/createV1")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                   @RequestParam("title") String title,
                                   @RequestParam("contents") String contents) {
        Question newQuestion = new Question(writer, title, contents, 0);
        questionRepository.insert(newQuestion);
        return "redirect:/";

    }

    @PostMapping("qna/createV2")
    public String createQuestionV2(@ModelAttribute Question question) {
        questionRepository.insert(question);
        log.info(question.toString());
        System.out.println(question.toString());
        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */

    /**
     * TODO: updateQuestion
     */

}
