package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.service.QuestionService;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    /**
     * TODO: showQuestionForm
     */
    @RequestMapping("/qna/form")
    public String showQuestionForm(HttpSession session) {

        if (UserSessionUtils.isLoggedIn(session)) {
            return "/qna/form";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @RequestMapping(value = "/qna/create")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                   @RequestParam("title") String title,
                                   @RequestParam("contents") String contents) {
        Question question = new Question(writer, title, contents, 0);
        questionService.save(question);
        return "redirect:/";
    }

    public String createQuestionV2(@ModelAttribute Question question) {
        questionService.save(question);
        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    @RequestMapping(value = "/qna/updateForm/{questionId}")
    public String showUpdateQuestionFormV1(@PathVariable("questionId") int questionId, HttpSession session,
                                           Model model) {
        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/";
        }

        Question question = questionService.findById(questionId);
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    public String showUpdateQuestionFormV2(@PathVariable("questionId") int questionId,
                                           @SessionAttribute(name = "user", required = false) User user,
                                           Model model) {
        if (user == null) {
            return "redirect:/";
        }

        Question question = questionService.findById(questionId);
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    /**
     * TODO: updateQuestion
     */
    @RequestMapping(value = "/qna/update")
    public String updateQuestion(@ModelAttribute Question question) {
        questionService.update(question);
        return "redirect:/";
    }
}
