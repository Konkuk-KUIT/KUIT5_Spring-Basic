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
import org.springframework.web.bind.annotation.SessionAttribute;

import java.sql.Date;
import java.time.LocalDate;


@RequiredArgsConstructor
@RequestMapping("/qna")
@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    @RequestMapping("/form")
    public String showQuestionForm(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            return "/qna/form";
        }

        return "redirect:/user/login";
    }



//   @RequestMapping("/create")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                   @RequestParam("title") String title,
                                   @RequestParam("contents") String contents) {
        Question created = new Question(writer, title, contents, 0);

        questionRepository.insert(created);

        return "redirect:/";
    }


    @RequestMapping("/create")
    public String createQuestionV2(@ModelAttribute Question created) {

        created.setCountOfAnswer(0);
        questionRepository.insert(created);

        return "redirect:/";
    }




//    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam("questionId")Integer questionId,
                                           HttpServletRequest request,
                                           Model model){

        Question question = questionRepository.findByQuestionId(questionId);

        HttpSession session = request.getSession(false);
        User user = null;
        String questionWriter = question.getWriter();

        if(session != null){
            user = (User)session.getAttribute("user");
        }

        if(user != null && user.getName().equals(questionWriter)){
            request.setAttribute("question", question);
            return "/qna/updateForm";
        }

        return "redirect:/";
    }

    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam("questionId")Integer questionId,
                                           @SessionAttribute(name="user") User user,
                                           Model model){

        Question question = questionRepository.findByQuestionId(questionId);
        String questionWriter = question.getWriter();



        if(user != null && user.getName().equals(questionWriter)){
            model.addAttribute("question", question);
            return "/qna/updateForm";
        }

        return "redirect:/";
    }





    @RequestMapping("/update")
    public String updateQuestion(final @RequestParam("questionId") Integer questionId,
                                 final @RequestParam("title") String title,
                                 final @RequestParam("contents") String contents){
        Question original = questionRepository.findByQuestionId(questionId);
        System.out.println(original);
        int originalCOA = original.getCountOfAnswer();
        String originalWriter = original.getWriter();

        Question updated = new Question(
                questionId,
                originalWriter,
                title,
                contents,
                Date.valueOf(LocalDate.now()),
                originalCOA);

        questionRepository.update(updated);

        System.out.println(updated);
        return"redirect:/qna/show?questionId="+questionId;

    }

}
