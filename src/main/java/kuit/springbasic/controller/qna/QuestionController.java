package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionRepository questionRepository;

    /**
     * TODO: showQuestionForm
     */

    @GetMapping("qna/form")
    public String showQuestionForm(HttpSession session) {
        if (UserSessionUtils.isLogined(session)) {
            return "qna/form";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */

    //@PostMapping("/qna/create")
    public String createQuestionV1(@RequestParam String writer,
                                 @RequestParam String title,
                                 @RequestParam String contents) {

        Question question = new Question(writer, title, contents);
        questionRepository.insert(question);
        return "redirect:/";
    }

    @RequestMapping("/qna/create")
    public String createQuestionV2(@ModelAttribute Question question) {
        questionRepository.insert(question);
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
