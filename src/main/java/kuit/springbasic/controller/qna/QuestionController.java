package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionRepository questionRepository;
    /**
     * TODO: showQuestionForm
     */
    @RequestMapping("/qna/form")
    public String showQuestionForm(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("user") == null){
            return "redirect:/user/loginForm";
        }
        return "qna/form";
    }
    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @RequestMapping("/qna/create")
    public String createQuestion(@RequestParam("writer")String writer,
                                 @RequestParam("title")String title,
                                 @RequestParam("contents")String contents){
        Question question = new Question(writer,title,contents,0);
        questionRepository.insert(question);
        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    @RequestMapping("/qna/updateForm")
    public String showUpdateQuestionForm(@RequestParam("questionId")int questionId,
                                         Model model) {
        Question question = questionRepository.findByQuestionId(questionId);
        model.addAttribute("question", question);
        return "qna/updateForm";
    }
    /**
     * TODO: updateQuestion
     */
    @RequestMapping("/qna/update")
    public String updateQuestion(@ModelAttribute Question question,
                                 Model model){
        Question updateQuestion = questionRepository.findByQuestionId(question.getQuestionId());
        updateQuestion.setTitle(question.getTitle());
        updateQuestion.setContents(question.getContents());
        questionRepository.update(updateQuestion);
        model.addAttribute("question", updateQuestion);
        return "qna/show";
    }

}
