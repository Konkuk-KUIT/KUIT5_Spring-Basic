package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QuestionController {

    private final MemoryQuestionRepository questionRepository;

    /**
     * TODO: showQuestionForm
     */
    @GetMapping("/form")
    public String showForm(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/user/login";
        }
        return "qna/form"; // /WEB-INF/views/qna/form.jsp 로 연결됨
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @PostMapping("/create")
    public String createQuestion(@RequestParam("writer") String writerFromForm,
                                 @RequestParam("title") String title,
                                 @RequestParam("contents") String contents,
                                 HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/user/login";
        }


        Question question = new Question();
        question.setWriter(writerFromForm);
        question.setTitle(title);
        question.setContents(contents);
        question.setCreatedDate(new Date(System.currentTimeMillis()));


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

