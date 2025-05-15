package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@Controller
public class QuestionController {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * TODO: showQuestionForm
     */
    @GetMapping("/qna/form")
    public String showQuestionForm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            return "qna/form";
        }

        return "redirect:/user/login";
    }


    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @PostMapping("/qna/create")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                   @RequestParam("title") String title,
                                   @RequestParam("contents") String contents) {
        questionRepository.insert(new Question(
                writer, title, contents, 0
        ));

        return "redirect:/";
    }

    //@PostMapping("/qna/create")
    public String createQuestionV2(@ModelAttribute Question newQuestion) {
        questionRepository.insert(new Question(
                newQuestion.getWriter(),
                newQuestion.getTitle(),
                newQuestion.getContents(),
                0
        ));

        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    //@GetMapping("/qna/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam("questionId") int questionId,
                                           HttpServletRequest request,
                                           Model model) throws AccessDeniedException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Question question = questionRepository.findByQuestionId(questionId);

        if (question.getWriter().equals(user.getUserId())) {
            model.addAttribute("question", question);
            return "qna/updateForm";
        }

        // 403 Forbidden
        // view에서 ui를 가리긴 한다
        throw new AccessDeniedException("게시글 작성자가 아닙니다");
    }

    @GetMapping("/qna/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam("questionId") int questionId,
                                           @SessionAttribute("user") User user,
                                           Model model) throws AccessDeniedException {
        Question question = questionRepository.findByQuestionId(questionId);

        if (question.getWriter().equals(user.getUserId())) {
            model.addAttribute("question", question);
            return "qna/updateForm";
        }

        // 403 Forbidden
        throw new AccessDeniedException("게시글 작성자가 아닙니다");
    }



    /**
     * TODO: updateQuestion
     */
    @PostMapping("/qna/update")
    public String updateQuestion(@RequestParam("questionId") int questionId,
                                 @RequestParam("title") String title,
                                 @RequestParam("contents") String contents){
        Question beforeUpdateQuestion = questionRepository.findByQuestionId(questionId);
        beforeUpdateQuestion.setTitle(title);
        beforeUpdateQuestion.setContents(contents);
        questionRepository.update(beforeUpdateQuestion);

        return "redirect:/qna/show?questionId=" + questionId;
    }

    @GetMapping("qna/delete")
    public String deleteQuestion(@RequestParam("questionId") int questionId) {
        questionRepository.deleteByQuestionId(questionId);

        return "redirect:/";
    }
}
