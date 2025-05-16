package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {

    private final QuestionRepository questionRepository;

    /**
     * TODO: showQuestionForm
     */
    @RequestMapping("/form")
    public String showQuestionForm(HttpSession session){
        if (UserSessionUtils.isLoggedIn(session)){
            return "qna/form";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @RequestMapping("/create")
    public String createQuestionV1(
            @RequestParam("writer") String writer,
            @RequestParam("title") String title,
            @RequestParam("contents") String contents
    ){
        Question question = new Question(writer, title, contents, 0);
        questionRepository.insert(question);
        return "redirect:/";
    }

    public String createQuestionV2(
            @ModelAttribute Question question
    ){
        questionRepository.insert(question);
        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    @RequestMapping("/updateForm/{questionId}")
    public String updateQuestionV1(
            @PathVariable("questionId") String questionId,
            HttpServletRequest request,
            Model model
    ){
        if (!UserSessionUtils.isLoggedIn(request.getSession())){
            return "redirect:/";
        }
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    public String updateQuestionV2(
            @PathVariable("questionId") String questionId,
            @SessionAttribute(name = "user", required = false) User user,
            Model model
    ){
        if (user == null){
            return "redirect:/";
        }
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    /**
     * TODO: updateQuestion
     */
    @RequestMapping("/update")
    public String updateQuestion(@ModelAttribute Question question){
        questionRepository.update(question);
        return "redirect:/";
    }

}
