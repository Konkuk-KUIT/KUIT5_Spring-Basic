package kuit.springbasic.controller.qna;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QuestionController {

    /**
     * TODO: showQuestionForm
     */
    @GetMapping("/form")
    public String showQuestionForm() {
        return "/qna/form";
    }
    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */

    /**
     * TODO: updateQuestion
     */

}
