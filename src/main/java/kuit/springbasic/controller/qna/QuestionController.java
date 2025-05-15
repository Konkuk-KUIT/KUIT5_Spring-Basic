package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

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
        log.info(newQuestion.toString());
        return "redirect:/qna/show?questionId=" + newQuestion.getQuestionId();

    }

    @PostMapping("qna/createV2")
    public String createQuestionV2(@ModelAttribute Question question) {
        questionRepository.insert(question);
//        log.info(question.toString());
        return "redirect:/qna/show?questionId=" + question.getQuestionId();
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    @GetMapping("/qna/updateV1")
    public String showUpdateQuestionFormV1(@RequestParam("questionId") int questionId,
                                           HttpServletRequest request,
                                           Model model) {
        // 유저와 글쓴이의 이름이 같은지 비교를 하고
        // model에 question을 담아서 넘기기
        HttpSession session = request.getSession();
        User value = (User) session.getAttribute("user");

        Question question = questionRepository.findByQuestionId(questionId);

        if (question.getWriter().equals(value.getName())) {
            model.addAttribute("question", question);
            return "qna/updateForm";
        }
        // 홈 말고 다른곳으로 리다이렉터해서 더 좋아지는 방법은 없나
        return "redirect:/";
    }

    @GetMapping("/qna/updateV2")
    public String showUpdateQuestionFormV2(@RequestParam("questionId") int questionId,
                                           @SessionAttribute(name = "user") User loginedUser,
                                           Model model) {
        Question question = questionRepository.findByQuestionId(questionId);
        if (question.getWriter().equals(loginedUser.getName())) {
            model.addAttribute("question", question);
            return "qna/updateForm";
        }
        return "redirect:/";
    }

    /**
     * TODO: updateQuestion
     */
    @PostMapping("/qna/update")
    public String updateQuestion(@ModelAttribute("question") Question question) {
        Question originalQuestion = questionRepository.findByQuestionId(question.getQuestionId());

        originalQuestion.setContents(question.getContents());
        originalQuestion.setTitle(question.getTitle());

        questionRepository.update(originalQuestion);
//        log.info(originalQuestion.toString());
        return "redirect:/qna/show?questionId=" + question.getQuestionId();
    }

    /**
     * TODO : deleteQuestion
     */
    @GetMapping("/qna/delete")
    public String deleteQuestion(@RequestParam("questionId") int questionId) {
        questionRepository.deleteById(questionId);
        return "redirect:/";
    }
}
