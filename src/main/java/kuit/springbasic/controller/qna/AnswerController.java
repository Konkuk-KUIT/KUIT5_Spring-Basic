package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.domain.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/qna")
@Controller
public class AnswerController {

    private final AnswerRepository answerRepository;


//    @PostMapping("/addAnswer")
    public void addAnswerV0(@RequestParam("questionId")Integer questionId,
                                           @RequestParam("writer")String writer,
                                           @RequestParam("contents")String contents,
                                           HttpServletResponse response) throws IOException {

        Answer answer = new Answer(questionId, writer, contents);

        Answer inserted = answerRepository.insert(answer);


        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> map = new HashMap<>();
        map.put("answer",inserted);


        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);

        response.getWriter().write(json);
    }

    // 모르겠습니다...ㅜㅜ
//    @PostMapping("/addAnswer")
    public ModelAndView addAnswerV1(@RequestParam("questionId")Integer questionId,
                            @RequestParam("writer")String writer,
                            @RequestParam("contents")String contents,
                            Model model){

        Answer answer = new Answer(questionId, writer, contents);

        Answer inserted = answerRepository.insert(answer);
        ModelAndView mav = new ModelAndView("/qna/show");

        mav.addObject("answer", inserted);
        return mav;
    }

//    @PostMapping("/addAnswer")
    @ResponseBody
    public Map<String, Object> addAnswerV2(@RequestParam("questionId")Integer questionId,
                            @RequestParam("writer")String writer,
                            @RequestParam("contents")String contents){

        Answer answer = new Answer(questionId, writer, contents);

        Answer inserted = answerRepository.insert(answer);
        Map<String, Object> map = new HashMap<>();
        map.put("answer",inserted);

        return map;
    }

    @PostMapping("/addAnswer")
    @ResponseBody
    public Map<String, Object> addAnswerV3(@ModelAttribute Answer answer){

        Answer inserted = answerRepository.insert(answer);
        Map<String, Object> map = new HashMap<>();
        map.put("answer",inserted);

        return map;
    }
}
