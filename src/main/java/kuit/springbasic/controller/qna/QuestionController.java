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

import java.sql.Date;
import java.time.LocalDate;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {

    private final QuestionRepository questionRepository;

    @GetMapping("/form")
    public String showQuestionForm(HttpServletRequest request) {
        if (UserSessionUtils.isLoggedIn(request.getSession())) {
            return "qna/form";
        }
        return "redirect:/user/loginForm";
    }

    //    @PostMapping("/create")
    public String createQuestionV1(@RequestParam String writer,
                                   @RequestParam String title,
                                   @RequestParam String contents) {
        questionRepository.insert(new Question(writer, title, contents, 0));

        return "redirect:/";
    }

    @PostMapping("/create")
    public String createQuestionV2(@ModelAttribute Question question) {
        question.setQuestionId(0);
        question.setCreatedDate(Date.valueOf(LocalDate.now()));
        question.setCountOfAnswer(0);

        questionRepository.insert(question);

        return "redirect:/";
    }

//    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam int questionId,
                                           HttpServletRequest request,
                                           Model model) {
        HttpSession session = request.getSession();

        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/user/loginForm";
        }

        Question question = questionRepository.findByQuestionId(questionId);
        User user = UserSessionUtils.getUserFromSession(session);
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);

        return "qna/updateForm";
    }

    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam("questionId") int questionId,
                                           @SessionAttribute(USER_SESSION_KEY) User user,
                                           Model model) {
        if (user == null) {
            return "redirect:/user/loginForm";
        }

        Question question = questionRepository.findByQuestionId(questionId);
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }

        model.addAttribute("question", question);

        return "qna/updateForm";
    }

    @PostMapping("/update")
    public String updateQuestion(@RequestParam("questionId") int questionId,
                                 @RequestParam("title") String title,
                                 @RequestParam("contents") String contents,
                                 HttpServletRequest req) {
        Question question = questionRepository.findByQuestionId(questionId);

        question.updateTitleAndContents(title, contents);
        questionRepository.update(question);

        return "redirect:/";
    }
}
